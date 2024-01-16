/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/. */

package dev.ligature.wander.zeromq

import org.zeromq.{ZMQ, ZContext, SocketType}

import dev.ligature.wander.run as runWander
import dev.ligature.wander.WanderValue
import dev.ligature.wander.printWanderValue
import dev.ligature.wander.libraries.loadFromPath
import dev.ligature.wander.printResult
import java.nio.file.Path
import java.nio.file.Paths
import java.io.File
import dev.ligature.wander.libraries.std
import dev.ligature.wander.*
import com.typesafe.scalalogging.Logger
import dev.ligature.wander.libraries.*

private class WanderZServer(val port: Int) extends Runnable with AutoCloseable {
  private val zContext = ZContext()

  override def run(): Unit =
    val socket = zContext.createSocket(SocketType.REP)
    socket.bind(s"tcp://localhost:$port")
    var continue = true
    while (!Thread.currentThread().isInterrupted() && continue)
      try
        val query = String(socket.recv(0), ZMQ.CHARSET) // blocks waiting for a request
        loadFromPath(File(sys.env("WANDER_LIBS")).toPath(), std()) match
          case Left(value) => throw RuntimeException(value)
          case Right(environment) =>
            val request = runWander(query, wmdn)
            request match {
              case Left(err) => throw RuntimeException(err)
              case Right((WanderValue.Module(request)), _) => 
                val result = runRequest(request, std())
                socket.send(result.getBytes(ZMQ.CHARSET), 0)
              case _ => ???
            }
      catch case e =>
        socket.close()
        zContext.close()
        e.printStackTrace()
        continue = false

  override def close(): Unit = zContext.close()
}

def runRequest(request: Map[Field, WanderValue], environment: Environment): String = {
  val action = request.get(Field("action"))
  val script = request.get(Field("script"))
  (action, script) match {
    case (Some(WanderValue.String("run")), Some(WanderValue.String(script))) =>
      run(script, environment) match {
        case Left(err) =>
          printWanderValue(WanderValue.Module(Map((Field("error") -> WanderValue.String(err.userMessage)))))
        case Right(value) => 
          printWanderValue(WanderValue.Module(Map((Field("result") -> value(0)))))
      }
    case _ => 
      printWanderValue(WanderValue.Module(Map((Field("error") -> WanderValue.String(s"No match - $action - $script")))))
  }
}

def runServer(port: Int): AutoCloseable = {
  val server = WanderZServer(port)
  val thread = Thread(server)
  thread.start()
  new AutoCloseable {
    def close(): Unit = server.close()
  }
}

@main def main =
  val logger = Logger("name")
  val server = WanderZServer(4200)
  server.run()

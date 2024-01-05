/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package dev.ligature.wander

import dev.ligature.wander.WanderValue
import scala.collection.mutable.Set
import scala.util.boundary
import scala.util.boundary.break

case class Environment(
    scopes: List[Map[Field, (Tag, WanderValue)]] = List(Map())
) {
  def eval(expressions: Seq[Expression]): Either[WanderError, (WanderValue, Environment)] = {
    var env = this
    var lastResult: Option[WanderValue] = None
    val err =
      boundary:
        expressions.foreach { expression =>
          dev.ligature.wander.eval(expression, env) match {
            case Left(value) => boundary.break(value)
            case Right((value, environment)) =>
              env = environment
              lastResult = Some(value)
          }
        }
    (lastResult, err) match {
      case (_, err: WanderError) => Left(err)
      case (None, _)             => Right((WanderValue.Nothing, env))
      case (Some(value), _)      => Right((value, env))
    }
  }

  def newScope(): Environment =
    Environment(
      this.scopes.appended(Map())
    )

  def bindVariable(
      taggedField: TaggedField,
      wanderValue: WanderValue
  ): Either[WanderError, Environment] =
    this.checkTag(taggedField.tag, wanderValue) match {
      case Left(value) => Left(value)
      case Right(value) =>
        val currentScope = this.scopes.last
        val newVariables = currentScope + (taggedField.field -> (taggedField.tag, wanderValue))
        val oldScope = this.scopes.dropRight(1)
        Right(
          Environment(
            oldScope.appended(newVariables)
          )
        )
    }

  def read(field: Field): Either[WanderError, WanderValue] = ???
  //   var currentScopeOffset = this.scopes.length - 1
  //   while (currentScopeOffset >= 0) {
  //     val currentScope = this.scopes(currentScopeOffset)
  //     if (currentScope.contains(name)) {
  //       return Right(currentScope(name)._2)
  //     }
  //     currentScopeOffset -= 1
  //   }
  //   Left(WanderError(s"Could not find ${name} in scope."))

  def read(fieldPath: FieldPath): Either[WanderError, WanderValue] = ???
  //   var currentScopeOffset = this.scopes.length - 1
  //   while (currentScopeOffset >= 0) {
  //     val currentScope = this.scopes(currentScopeOffset)
  //     if (currentScope.contains(name)) {
  //       return Right(currentScope(name)._2)
  //     }
  //     currentScopeOffset -= 1
  //   }
  //   Left(WanderError(s"Could not find ${name} in scope."))

  def importModule(name: FieldPath): Either[WanderError, Environment] =
    ???
    // var currentEnvironemnt = this
    // this.scopes.foreach { scope =>
    //     scope
    //     .filter { (n, _) => n.startsWith(name.names.head) }
    //     .foreach( (n, v) =>
    //       val relName = n.drop(name.length)
    //       currentEnvironemnt = currentEnvironemnt.bindVariable(TaggedField(relName, Tag.Untagged), v._2).getOrElse(???)
    //     )
    // }
    // this.functions
    //   .filter { f => f.name.startsWith(name) }
    //   .foreach(f =>
    //     val relName = f.name.drop(name.length)
    //     currentEnvironemnt = currentEnvironemnt.bindVariable(TaggedField(relName, Tag.Untagged), WanderValue.Function(f)).getOrElse(???)
    //   )
    // //TODO handle properties
    // Right(currentEnvironemnt)

//   def addHostFunctions(functions: Seq[(Name, HostFunction)]): Environment =
//     var currentEnvironemnt = this
//     functions.foreach(f =>
//       currentEnvironemnt = currentEnvironemnt
//         .bindVariable(TaggedField(f._1, Tag.Untagged), WanderValue.Function(f._2))
//         .getOrElse(???)
//     )
//     currentEnvironemnt.copy(functions = currentEnvironemnt.functions ++ functions)

//   def addHostProperties(properties: Seq[HostProperty]): Environment =
//     var currentEnvironemnt = this
// //    properties.foreach(p => currentEnvironemnt = currentEnvironemnt.bindVariable(TaggedField(p.name, p.resultTag), p.))
//     this.copy(properties = this.properties ++ properties)

  def checkTag(tag: Tag, value: WanderValue): Either[WanderError, WanderValue] =
    tag match {
      case Tag.Untagged    => Right(value)
      case Tag.Single(tag) => checkSingleTag(tag, value)
      case Tag.Chain(tags) => ??? /// checkFunctionTag(tags, value)
    }

  private def checkSingleTag(tag: Function, value: WanderValue): Either[WanderError, WanderValue] =
    tag match {
      case hf: HostFunction =>
        hf.fn(Seq(value), this) match {
          case Right((WanderValue.Bool(true), _)) => Right(value)
          case Right((WanderValue.Bool(false), _)) =>
            Left(WanderError("Value failed Tag Function."))
          case Left(err) => Left(err)
          case _         => Left(WanderError("Invalid Tag, Tag Functions must return a Bool."))
        }
      case lambda: Lambda =>
        assert(lambda.lambda.parameters.size == 1)
        var environment = this.newScope()
        // environment = environment
        //   .bindVariable(TaggedField(lambda.lambda.parameters.head, Tag.Untagged), value)
        //   .getOrElse(???)
        dev.ligature.wander.eval(lambda.lambda.body, environment) match {
          case Right((WanderValue.Bool(true), _)) => Right(value)
          case Right((WanderValue.Bool(false), _)) =>
            Left(WanderError("Value failed Tag Function."))
          case Left(err) => Left(err)
          case _         => Left(WanderError("Invalid Tag, Tag Functions must return a Bool."))
        }
      case _ => Left(WanderError(s"${tag} was not a valid tag."))
    }

  private def checkFunctionTag(
      tags: Seq[Function],
      value: WanderValue
  ): Either[WanderError, WanderValue] =
    Right(value)
    // this.read(tag) match {
    //   case Right(WanderValue.HostFunction(hf)) =>
    //     hf.fn(Seq(value), this) match {
    //       case Right((WanderValue.Bool(true), _))  => Right(value)
    //       case Right((WanderValue.Bool(false), _)) => Left(WanderError("Value failed Tag Function."))
    //       case Left(err)                           => Left(err)
    //       case _ => Left(WanderError("Invalid Tag, Tag Functions must return a Bool."))
    //     }
    //   case Right(WanderValue.Lambda(lambda)) =>
    //     ???
    //   case Left(err) => Left(err)
    //   case _         => Left(WanderError(s"${tag.name} was not a valid tag."))
    // }
}

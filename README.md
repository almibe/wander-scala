# wander-scala
An implementation of the Wander programming language using Scala 3.

### Building Wander's NPM Artifact

To build the Scala.js artifact for Wander follow these steps in the command line from the root of this project.

```
sbt "project wanderJS" fullOptJS
cd wander
npm publish
```
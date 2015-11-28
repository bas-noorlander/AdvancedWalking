# AdvancedWalking
A navmesh pathfinder/walking library for Tribot.

##### What is AdvancedWalking?
It's the next generation of pathfinding in osrs botting, written specifically for the Tribot client, with the aim to replace WebWalking.

##### Features
-  Replacement/Alternative to WebWalking
-  Shortest-route pathfinding (even over long distances!)
-  Door/gate/stairs support.
-  Teleport support.
-  Agility shortcut support.
-  Intelligent bank cache system to see if teleports are available.
-  Very scripter friendly.
-  Optional: fallback to webwalking in case something fails.
- Code is 100% free and open source, including code for the mesh generator and updaters. Most code is unit tested as well(WIP).

##### Installation
Simply clone this repository into your script and easily keep it up to date using `git pull`.

Alternatively, you can download a zip of the project using the in the menu.

##### Usage
Instead of using your current walking method ie: `WebWalking.walkTo(destination)` you can simply use `AdvancedWalking.travel(destination)`. Note that the `travel` method will try to use teleports/agility shortcuts if they are available. You can also call `AdvancedWalking.walk(destination)` to force walking only. More options are available, see below.

If you only want to find a path to the destination (without going there). You can use `AdvancedWalking.findPath(destination)`. Which will generate a path from the players' position to the destination. Alternatively use `AdvancedWalking.findPath(start, destination)` to use a different starting position. These functions return a `Path` object which contain the tiles and any actions it has to take (like climbing stairs, or optionally teleport). View the `Path` [javadocs]() for more information.

##### Code features
Full javadocs are available [here]().


##### Advanced usage
Want more out of AdvancedWalking? Thats great! it is made with customization in mind and should be easy to extend.
Some key features:

Use your own walking methods by creating a class that implements `IWalker`, after which you can set it by `AdvancedWalking.setWalker()`. (you can even switch between walking algorithms during runtime!).

You can also use your own pathfinding algorithm! implement `IPathfinder` and use `AdvancedWalking.setPathfinder()`.

Want to customize the mesh itself? You can customize the Generator as you like, however this means you will have to keep your own version of the mesh available somewhere. (by default it downloads and uses a mesh made by me). However, i made all preparations for this, you can even change the updater to download your version!

Want to change the generator's shape? By default they are polytopes. But you can make your own, like circles or rainbows.
Extend `AbstractShape` and `IShapeFactory`. The included SampleGeneratorScript shows a very simple solution on how to use them.

You also have complete control on how the updater works, so you can point it to your own version of the mesh if you like. You can do so by extending `AbstractUpdater` and change your class that implements `IPathfinder` to use that updater.


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
-  Code is 100% free and open source, including code for the mesh generator and updaters. Most code is unit tested as well(WIP).

##### Installation
Simply clone this repository into your script namespace and zip it together with your script when uploading. This way you can easily keep it up to date using `git pull`.

Alternatively, you can download a zip of the project using the link in the menu.

##### Usage
Instead of using your current walking method ie: `WebWalking.walkTo(destination)` you can simply use `AdvancedWalking.travel(destination)`. Note that the `travel` method will try to use teleports/agility shortcuts if they are available. You can also call `AdvancedWalking.walk(destination)` to force walking only. There are many features available, i'll try to explain some major ones below.

If you only want to find a path to the destination (without going there). You can use `AdvancedWalking.findPath(destination)`. Which will generate a path from the players' position to the destination. Alternatively use `AdvancedWalking.findPath(start, destination)` to use a different starting position. These functions return a `Path` object which contain the tiles and any actions it has to take (like climbing stairs, or optionally teleport). View the `Path` [javadocs](https://laniax.github.io/AdvancedWalking/scripts/AdvancedWalking/Game/Path/Path.html) for more information.

Note that the first time that a method is called, be it #travel() or #walk() (or any other). AdvancedWalking will set everything up and check for updates first. This could be undesired and you might want to do this when your script starts instead. You can call `AdvancedWalking.prepare()` to do so.

##### Code features
Full javadocs are available [here](https://laniax.github.io/AdvancedWalking/).


##### Advanced usage
Want more out of AdvancedWalking? Thats great! it is made with customization in mind and should be easy to extend.
Some key features:

Use your own walking methods by creating a class that implements `IWalker`, after which you can set it by `AdvancedWalking.setWalker()`. (you can even switch between walking algorithms during runtime!).

You can also use your own pathfinding algorithm! implement `IPathfinder` and use `AdvancedWalking.setPathfinder()`.

Want to customize the mesh itself? You can customize the Generator as you like, however this means you will have to keep your own version of the mesh available somewhere. (by default it downloads and uses a mesh made by me). However, i made all preparations for this, you can even change the updater to download your version!

Want to change the generator's shape? By default they are polytopes. But you can make your own, like circles or rainbows.
Extend `AbstractShape` and `IShapeFactory`. The included SampleGeneratorScript shows a very simple solution on how to use them.

You also have complete control on how the updater works, so you can point it to your own version of the mesh if you like. You can do so by extending `AbstractUpdater` and change your class that implements `IPathfinder` to use that updater.

Furthermore, there is an advanced Event system which let you hook into AdvancedWalking to modify data or trigger your own code. Exact code usage is still under development, but will be along the lines of: `EventManager.listen(yourClassThatImplementsIOnFindPath);` or `EventManager.listen(yourClassThatImplementsIOnUpdatedMesh);` which will allow you to run additional code or make adjustments to the parameters before it is processed further.

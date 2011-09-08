[![][Project Logo]][Website]
BabelCraft
==========
Automatic chat translation plugin and API for Bukkit-based Minecraft servers.

Copyright &copy; 2011, CraftFire <dev@craftfire.com>  
BabelCraft is licensed under [Creative Commons CC BY-NC-ND 3.0][License]

Visit our [website][Website].  
Get support on our [Bukkit forum thread][Forum].  
Track and submit issues and bugs on our [issue tracker][Issues].

[![][Twitter Button]][Twitter] [![][Facebook Button]][Facebook]

Source
------
The latest and greatest source can be found on [GitHub].  
Download the latest builds from [Jenkins].  
View the latest [Javadoc].

Compiling
---------
BabelCraft uses Maven to handle its dependencies.

BabelCraft requires Bukkit and other dependencies (preferrably the latest versions or source).  
* Install [Maven 2 or 3](http://maven.apache.org/download.html)  
* Checkout this repo and run: `mvn clean package`

Other required dependencies:  
* SpoutAPI  
* BukkitEx  
* JSON  
* MaxMinds GeoIP  
* Google Translation API (Unofficial)  
* Microsoft Translation API (Unofficial)  
* myGengo Translation API (Official)

Coding and Pull Request Conventions
-----------------------------------
* We generally follow the Sun/Oracle coding standards.
* No tabs; use 4 spaces instead.
* No trailing whitespaces.
* No CRLF line endings, LF only, put your gits 'core.autocrlf' on 'true'
* No 80 column limit or 'weird' mid-statement new lines.
* The number of commits in a pull request should be kept to a minimum (squish them into one most of the time - use common sense!).
* No merges should be included in pull requests unless the pull request's purpose is a merge.
* Pull requests should be tested (does it compile AND does it work?) before submission.

Follow the above conventions if you want your pull requests accepted.

[![][Donation Button]][Donation]

[Project Logo]: http://assets.craftfire.com/img/logo/babelcraft_564x93.png
[Author Logo]: http://assets.craftfire.com/img/logo/craftfire.png
[License]: http://www.creativecommons.org/licenses/by-nc-nd/3.0/
[Website]: http://www.craftfire.com
[Forum]: http://forums.bukkit.org/threads/10839/
[GitHub]: https://github.com/CraftFire/BabelCraft
[Javadoc]: http://jddev.craftfire.com/babelcraft
[Jenkins]: http://ci.craftfire.com/job/BabelCraft
[Issues]: https://issues.craftfire.com
[Twitter]: http://www.twitter.com/CraftFireDev
[Twitter Button]: http://cdn.getspout.org/img/button/twitter_follow_us.png
[Facebook]: http://www.facebook.com/CraftFire
[Facebook Button]: http://cdn.getspout.org/img/button/facebook_like_us.png
[Donation]: https://www.paypal.com/cgi-bin/webscr?hosted_button_id=QNJH72R72TZ64&item_name=BabelCraft+%28from+github.com%29&cmd=_s-xclick
[Donation Button]: http://cdn.getspout.org/img/button/donate_paypal_96x96.png

# Blitzkrieg

[![License](http://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

Blitzkrieg is an old project of mine, made around 2014, that I made as a part of my master's degree related
to comparison of search algorithms for choosing a move in a simple two-player game.

This is an attempt to keep it alive and maintained and perhaps expand upon in the future. Keep in mind that currently
it's just a testbed. There's no way to play it yourself, you can only watch the various AIs play each other.

![Showcase](https://github.com/Detharon/Blitzkrieg/blob/master/docs/Recording.gif)

## Supported languages

Blitzkrieg is available in the following languages:
- English (default)
- Polish

## Project structure 

Blitzkrieg is based on LibGDX and follows a basic structure of a multi-platform LibGDX project.

All logic resides in the **code** project, while the **desktop** one provides just a single window to launch it.
The **desktop** project is responsible for creating the executable. I'll describe the complete process once this
project has proper docs.

## Known issues

- Running all turns and then running a new game does not stop the previous game from running

## Planned features

- Russian & Spanish translation
- Map encoded as a JSON
- More than one map
- Play against AI
- Better summary screen
- And more...

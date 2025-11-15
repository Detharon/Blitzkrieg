# Blitzkrieg

Blitzkrieg is an old project of mine, made around 2014, that I made as a part of my master's degree related
to comparison of search algorithms for choosing a move in a simple two-player game.

This is an attempt to keep it alive and maintained and perhaps expand upon in the future.

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

- Region hitboxes aren't accurate, because of that, the neighbour region highlight is very wonky

# Catan

A video game based on the board game The Settlers of Catan

## Usage

### Requirement

- java jdk 10
- gradle at least 5.0

### Setup

1. clone the repository

```shell
git clone https://github.com/ocineh/Catan.git
```

3. If you are on a computer of the SCRIPT

```
source SCRIPT/envsetup
```

Set the proxy to allow gradle to download

### Run the game

For launching gui

```
gradlew run --args"gui"
```

For launching tui

```
gradlew run --args"tui"
```
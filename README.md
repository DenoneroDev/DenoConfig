# DenoConfig
JSON Config Library for Java

> ## Overview
> - [create](#create)
> - [prettify](#prettify)
> - [parse](#parse)
> - [fetch](#fetch)
> - [remove](#remove)
> - [clear](#clear)
> - [save](#save)

### Create
DenoConfig config = new DenoConfig(String path);
> Exaple path: /Configs/config.json

### Prettify
config.prettify();

### Parse
config.set(String key, Object value);
> Example key: test || test.test2
> Example value: "Test" || 1 || Map || List

### Fetch
config.get(String key);
> Example key: test || test.test2

### Remove
config.remove(String key);
> Example key: test || test.test2

### Clear
config.clear();

### Save
config.save();

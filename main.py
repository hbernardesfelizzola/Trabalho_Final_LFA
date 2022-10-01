import yaml



def read_yaml(file):
    with open(file, "r") as stream:
        try:
            config = yaml.load(stream)
            print(config)
        except yaml.YAMLError as exc:
            print(exc)
            print("\n")
    return config


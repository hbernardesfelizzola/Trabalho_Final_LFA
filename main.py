from asyncio.windows_events import NULL
from pickle import FALSE
import yaml



def read_yaml(file):
    with open(file, "r") as stream:
        try:
            config = yaml.safe_load(stream)
            print(config)
        except yaml.YAMLError as exc:
            print(exc)
            print("\n")
    return config

def execute_word(palavra, paths) -> bool :
    nodo = paths["praca"]
    for terminal in palavra:
        if nodo["caminhos"] != NULL and nodo["caminhos"][terminal] != NULL:
            nodo = nodo["caminhos"][terminal]
        else:
            return False
    
    if nodo["final"] != NULL:
        return nodo["final"]
    else:
        return False


paths = read_yaml("paths.yaml")

print(execute_word("ddde", paths))


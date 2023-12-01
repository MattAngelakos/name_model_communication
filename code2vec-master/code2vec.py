from vocabularies import VocabType
from config import Config
from interactive_predict import InteractivePredictor
from model_base import Code2VecModelBase
import socket
def load_model_dynamically(config: Config) -> Code2VecModelBase:
    assert config.DL_FRAMEWORK in {'tensorflow', 'keras'}
    if config.DL_FRAMEWORK == 'tensorflow':
        from tensorflow_model import Code2VecModel
    elif config.DL_FRAMEWORK == 'keras':
        from keras_model import Code2VecModel
    return Code2VecModel(config)


if __name__ == '__main__':
    HOST = "localhost"
    PORT = 8080
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect((HOST, PORT))
    data = sock.recv(1024)
    config = Config(set_defaults=True, load_from_args=True, verify=True)
    model = load_model_dynamically(config)
    config.log('Done creating code2vec model')
    if config.PREDICT:
        predictor = InteractivePredictor(config, model)
        data = predictor.predict()
        print("stop")
        sock.send((data+'\n').encode('utf-8'))
        #data = sock.recv(1024)
        #data = predictor.predict()
        #print("stop")
        #sock.sendall((data+'\n').encode('utf-8'))
    model.close_session()
    sock.shutdown(socket.SHUT_RDWR)
    sock.close()
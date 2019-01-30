#! /usr/bin/env python
# -*- coding: utf-8 -*-

import os
import sys
from gtts import gTTS

# dunno what this is yet. somehow related to UTF-8 and Mac issues
reload(sys)
sys.setdefaultencoding("utf-8")

# different microphones can return different level of signal
rms_multiplyer = 1
mp3_name = 'warning.mp3'
language = 'ru'

def get_message(rms):
    rms = int(rms)
    if rms < 100:
        return 'Потише!'
    elif rms < 150:
        return 'Тише!!'
    elif rms < 1500:
        return 'Тише! Слишком громко!'
    elif rms < 2000:
        return 'Слишком громко! на 30 децибел потише!'
    else: # Very LOUD
        return 'Да Вы совсем мох у ели!'

def main():
    try:
        rms = sys.argv[1] * rms_multiplyer
    except IndexError:
        print('no rms')
        rms = "4000"

    mytext = get_message(rms)
    tts = gTTS(text=mytext, lang=language, slow=False)
    tts.save(mp3_name)

    platform = sys.platform
    if platform == "linux" or platform == "linux2":
        os.system("mpg123 " + mp3_name)
    elif platform == "darwin":
        os.system("afplay " + mp3_name)

if __name__ == "__main__":
    main()
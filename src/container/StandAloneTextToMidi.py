from mido import MidiFile, MidiTrack, Message
import re


def textToTrack(file,output):
    f = open(file, 'r')
    data = f.read().replace(',','')
    if data[-1]=='\n':
        data = data[:-1];
        print("Hi")
    data = data.split('\n')
    mid = MidiFile()
    track = MidiTrack()
    mid.tracks.append(track)
    for x in data:
        var = re.split(r'\s+', x);
        msg ='note_on channel='+var[0]+' note='+var[1]+' velocity='+var[2]+' time='+var[3]
        print(msg)
        track.append(Message.from_str(msg))
    mid.tracks.append(track)
    mid.save(output)

textToTrack("ga1.txt","saved.mid") #<----Change your text file and saved file name here

from mido import MidiFile
from mido import MidiTrack
from mido import Message
import re;

'''
printing no meta Messages
'''
def printMidi(file):
    mid = MidiFile(file)
    for track in mid.tracks:
        for msg in track:
            if not msg.is_meta and msg.type == 'note_on' :
                print(msg)

'''
Midi Text to track
'''
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



def saveTrackMiditoText(file,output):
    mid = MidiFile(file)
    f = open(output,'w')
    for track in mid.tracks:
        for msg in track:
            if not msg.is_meta and msg.type == 'note_on':
                msg = str(msg)[8:].replace('channel=','').replace('note=', '').replace('velocity=', '').replace('time=', '')
                f.write(msg+'\n')
    f.close()

def lineToArray(a):
    var = re.split(r'\s+', a)
    var = var[:len(var)-1]
    nums = [int(n) for n in var]
    return var

def arraySubtraction(a,b):
    z = [0]* len(b)
    for i in range(0,len(b)):
        z[i] = abs(int(b[i])-int(a[i]))
    return z

def calculateTrainingFitness(input,output):
    l = list()
    diff = list()
    with open(input) as my_file:
        for line in my_file:
            l.append(lineToArray(line) )
    for i in range(1,len(l)):
        diff.append(arraySubtraction(l[i-1],l[i]))
    f = open(output, 'w')
    for x in diff:
        f.write(str(x).replace(',','').replace('[','').replace(']','')+'\n')

saveTrackMiditoText('grieg_walzer.mid','trainer.txt')
saveTrackMiditoText('xmas.mid','parent1.txt')
saveTrackMiditoText('movement.mid','parent2.txt')
saveTrackMiditoText('elise.mid','parent3.txt')
saveTrackMiditoText('Debussy_Passepied.mid','parent4.txt')
saveTrackMiditoText('Chopin_Vivace.mid','parent5.txt')
#saveTrackMiditoText('ravel_Variance.mid','parent6.txt')
calculateTrainingFitness('trainer.txt', 'fitness.txt')



# MusicGenerator


To convert your saved txt file to a midi file,
use the StandAloneTextToMidi.py file

The text file version of midi files are stored in the following format:

note_on channel=0 note=76 velocity=42 time=480
note_on channel=0 note=79 velocity=50 time=0
note_on channel=0 note=79 velocity=0 time=60


*note 76 = c#. you don't have to worry about what note is what. The parser handles it
*Velocity describes how hard a note is going to be hit
*Time represents the play time of the note. Don't worry about 480. It's a representation, the parset
    will convert it to seconds when saving to mid.

*midi files are 8 bits, so don't worry about channels, we'll stick to only 1 channel
*note_on represents if the note is going to be played. [off/on switch]

We'll make a fitness function from a sample song of our choosing.
The only relevant parts to use are note=76 velocity=42 time=480

for the first note we'll represent it as an array: [76, 42, 480]
for the 2nd note we'll represent it as an array: [79, 50, 0]
for the 3rd note we'll represent it as an array: [79, 0, 60]

***REPRESENTATION****
We'll represent the entire song as an ARRAY of arrays;
Entire song = [ [76, 42, 480]  , [79, 50, 0] , [79, 0, 60] ]
                    x0              x1              x2
***FITNESS FUNCTIONS****
our fitness function will also be an array of array.
fitness = [ y0, y1 .....] where y1 and y2 are also arrays of length 3: y0 = [ value, value, value]

how to formulate y0..y1.. etc:
y0 = absolute value of |x1 - x0| =  [79, 50, 0]  - [76, 42, 480]
                                 =  [3,8,480]
y1 = absolute value of |x2 - x1| =  [79, 0, 60]  - [79, 50, 0]
                                 =  [0,50,60]

We'll save the fitness array in a file (Comes Later)
Our Training set Is Complete!

To formulate music now, we can take any music file and convert it into
note_on channel=0 note=76 velocity=42 time=480 format and then, turn it into an
array of array as we did earlier. We'll perform crossover and mutations on it
and check how close it's fitness value compares with our saved fitness value.
Eg:
our Saved fitness [ [3,8,480] [0,50,60] ]

let new music's representation is
1st gene = [76, 0, 200] [72,0,600] [ 72, 50,520]
2nd gene =[72, 72, 480] [75,80,0] [ 75, 50,60]

for 1st gene:
out fitness =       [ [3, 8, 480] [0, 50, 60] ]
it's fitness =      [ [4, 0, 400] [0, 50, 80] ]
difference array=   [ [1, 8, 80]  [0,  2, 20] ]

2nd gene:
out fitness =       [ [3, 8, 480] [0, 50, 60] ]
it's fitness =      [ [3, 8 ,480] [0, 30, 60] ]
difference   =      [ [0, 0 ,  0] [0 ,20,  0]

2nd Gene has the smallest difference so the best fitness:

Remember that the fitness arrays must be for same length.
if either our training fitness has more values than the other or vice versa,
we'll truncate the one with the greater value. i.e if len(a) = 30 and len(b) =20,
we'll only use 20 elements from a. I.e: a = a[0 to 20]

Note that text files need to be saved in the same format as:
note_on channel=0 note=79 velocity=0 time=60











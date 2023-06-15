# Todo
* Graphical output
    + Colors per cell are between one and another based on sugar
    + Print map
* Agent
    + Die from starvation (consumption rate) or old age
    + Move to the most sugary cell in their vision
    + Eat sugar
    + One agent created at origin at map generation
    + Terminate program if all agents are dead
    + Vision 360deg (can be circle or square, take radius as parameter)
    + Several agents spawn
    - Print agent stats at process close (Lifetime, max sugar, death location)
    + Fix window scaling with grid size change
    + Consolidate constants and defaults into config
    + !Not sure how to access simplex noise as a library! Set max cell sugar based on 2d noise func
    + Randomized arrays for immune string and culture strings/arrays
    + Default lengths for the immune string and culture array should be settable from the config
    + Keep track of recently seen agents (ArrayList per-agent of last seen people)
    - Location of smallest distance between binaryStrings
    + Ondeath: Sugar in reserves is dumped on their current tile
    + Onspawn: Randomize range and metabolism
    + Make the agents divide at 100 hunger (except the new agent copies the values of the parent, but with small changes)
    - compatibility function to determine compatibility between two agents for reproduction based on age, hunger, and culture difference between
    - spice
    - trading of sugar and spice (mutually beneficial)
    - combat
    - pollution: when sugar is pulled, the cell's pollution increases, which decreases that cell's regrowth rate
    - viruses
* Read book

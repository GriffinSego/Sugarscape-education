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
    ~ Take stats of agents and print at process close (Lifetime, max sugar, death location)
    + Fix window scaling with grid size change
    + Consolidate constants and defaults into config
    x !Not sure how to access simplex noise as a library! Set max cell sugar based on 2d noise func
    + Randomized arrays for immune string and culture strings/arrays
    + Default lengths for the immune string and culture array should be settable from the config
    - Keep track of recently seen agents (ArrayList per-agent of last seen people)
    - Comparing boolean arrays and finding distance between them, and location of smallest distance between them
    - Ondeath: Sugar in reserves is dumped on their current tile
    - Onspawn: Randomize range and metabolism
* Read book

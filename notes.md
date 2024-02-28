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
    + Print agent stats at process close (Lifetime, max sugar, death location)
    + Fix window scaling with grid size change
    + Consolidate constants and defaults into config
    + !Not sure how to access simplex noise as a library! Set max cell sugar based on 2d noise func
    + Randomized arrays for immune string and culture strings/arrays
    + Default lengths for the immune string and culture array should be settable from the config
    + Keep track of recently seen agents (ArrayList per-agent of last seen people)
    + Location of smallest distance between binaryStrings
    + Ondeath: Sugar in reserves is dumped on their current tile
    + Onspawn: Randomize range and metabolism
    + Make the agents divide at 100 hunger (except the new agent copies the values of the parent, but with small changes)
    + compatibility function to determine compatibility between two agents for reproduction based on age, hunger, and culture difference between
    + spice
        + New "good"
        + different noise field for generation
        + Cells can have both sugar AND spice
        + Agents have inventories of both sugar AND spice
        + Unspiced agents die
        + Agents have different metabolisms for both sugar and spice
        + Agent vision takes into account spice on the formulation of whether spice or sugar is more necessary for short term survival
        ~ Read first couple chapters of epstein_axtell.pdf
    + Miosis (do not overwrite)
    + Deprecate mitosis
    - James Todd: GINI coefficient
    - trading of sugar and spice (mutually beneficial)
    - combat
    - towny map
    - pollution: when sugar is pulled, the cell's pollution increases, which decreases that cell's regrowth rate
    - viruses
    - fix low metabolism
    - inherit metabolism mostly
    - metabolism floor well above 0
    - fix error
    - test new BinaryString methods
* Read book

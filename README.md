## take_a_break_remake: A beach sunset simulation

This is a program that simulates a sunset at the beach.

I have long enjoyed "Homestar Runner" (http://www.homestarrunner.com), a website of comedic cartoons made in Macromedia Flash.

One time, a character watches a simulated beach sunset, complete with waves and seagulls, on his early 1980s computer.  [(screenshot of the original)](homestarrunner_original.png)  I enjoyed the idea and would have liked to see a modern, less pixellated version.  Since learning to program, I decided to try it myself.  All credit for the idea goes to Homestar Runner and its creators.

The original link can be found here: http://www.homestarrunner.com/take-a-break.exe

####Current features (2-26-17):
- Sunset simulation using gradients to depict sky, water, and sand; also has a sun and clouds.
- Cloud size and location is randomized.  Clouds near horizon are darker.
- Slider controls can set height of sun above horizon, as well as number of clouds present.
- Background colors (sky/water/sand) will change depending on height of sun.
- [Output image](output_170219.png)

####Upcoming changes:
- Cloud colors- brightness depending on sun position, shadows, shadow response to sun position
- Color response to clouds
- Possible options for colors (e.g. realistic vs vivid/idyllic colors)
- Animated waves
- Animation of clouds
- Animated objects (ships, birds, sailboats, palm trees, dolphins)
- Reflections
- Better-looking sun behavior
- Islands
- Adjustability (amount of water waves, boats, wind effects)
- Reflection of the sun on the water
- Sound effects of water and maybe wind/seagulls
- Possible wind response of objects


####Revision history:
- 2-26-17
  - Added clouds/cloud generator
  - Added slider controls to set number of clouds and sun's position in the sky above horizon.  Colors respond in real time, it looks great!
  - Added algorithms to adjust colors of background (sky/water/sand) according to position of sun in sky
  - Expanded screen size to include new controls
  - This update is my first exposure to slider controls and lambda expressions.
  - [Output image 1 (high sun, few clouds)](output_170226a.png)
  - [Output image 2 (low sun, many clouds)](output_170226b.png)

- 2-19-17
  - Created the program with gradient sky, water, sand areas and a setting sun.
  - [Output image](output_170219.png)

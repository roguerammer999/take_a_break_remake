## take_a_break_remake: A beach sunset simulation

This is a program that simulates a sunset at the beach.

I have long enjoyed "Homestar Runner" (http://www.homestarrunner.com), a website of comedic cartoons made in Macromedia Flash.

One time, a character watches [a simulated beach sunset called "take-a-break"](homestarrunner_original.png), complete with waves and seagulls, on his early 1980s computer.  I enjoyed the idea and would have liked to see a modern, less pixellated version.  Since learning to program, I decided to try it myself.  All credit for the idea goes to Homestar Runner and its creators.

The original link can be found here: http://www.homestarrunner.com/take-a-break.exe

#### Current features (3-19-17):
- Sunset simulation using gradients to depict sky, water, and sand; also has a sun and clouds.
- Cloud size and location is randomized within some constraints.  Clouds that pass in front of the sun become somewhat transparent.
- Cloud color depends on position of sun and height of cloud.  A lower sun also makes the top color of a two-color gradient more dominant, resulting in dark clouds with a bright sliver on the bottom.
- Slider and spinner controls can set height of sun above horizon, as well as number of clouds present.
- Background colors (sky/water/sand) will change depending on height of sun.  They also darken if the sun passes behind clouds.
- Simple animated waves that move from the horizon toward the sand, then break into white surf that fades and leaves wet sand that also fades.
- [Output image (not animated)](output_170319.png)

#### Upcoming changes:
- Possible options for colors (e.g. realistic vs vivid/idyllic colors)
- Animation of clouds
- Animated objects (ships, birds, sailboats, palm trees, dolphins)
- Reflections
- Reflection of the sun on the water
- Better-looking sun behavior
- Islands
- Adjustability (amount of water waves, boats, wind effects)
- Sound effects of water and maybe wind/seagulls
- Possible wind response of objects


#### Revision history:
- 3-19-17
  - Added animated waves.  They change color depending on the height of the sun.
  - Added animated surf/bubbles.
  - Added animated wet sand that dries out after the surf recedes.
  - [Output image (not animated)](output_170319.png)

- 3-08-17
  - Moved two methods to a different class to make code more workable.
  - Set clouds to turn transparent when passing in front of sun; this also causes the background colors to darken slightly.
  - Added spinner button controls to adjust sun height and cloud count.
  - Turned on antialiasing for smoother edges.
  - [Output image- shows different sky colors with sun at same height due to cloud cover](output_170308.png)

- 2-28-17
  - Stopped clouds from regenerating size/location when changing sun position.  Clouds now stay stationary.
  - Set cloud gradient colors to respond to sun position and height of cloud.  Gradient becomes more top-color dominant when the sun is low.
  - Allowed sun and corona to grow/shrink depending on position of sky.  The corona also changes color near the horizon.
  - [Output image 1 (high sun)](output_170228a.png)
  - [Output image 2 (low sun, this one looks very nice with the cloud glimmer)](output_170228b.png)

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

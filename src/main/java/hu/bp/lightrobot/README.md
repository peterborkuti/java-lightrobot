# LightRobot

The environment is a light bulb which is randomly switched off and on.

The robot has a LED. The LED should be switched on/off according to
the bulb state.

When the reward function is for synchron robot, the LED
should be in synchron with the bulb.
When the reward function is for asynchron robot, the LED
should be on when the bulb is off and vice versa. 

## Actions
The actions are

* 0 - switching off LED
* 1 - switching on LED

## States
* 0 - light bulb is off
* 1 - light bulb is on

## Rewards
### in synchron mode
* a positive number(1) when bulb is on and action is on
* a positive number(1) when bulb is off and action is off
* a negative number(-1) when bulb is on and action is off
* a negative number(-1) when bulb is off and action is on

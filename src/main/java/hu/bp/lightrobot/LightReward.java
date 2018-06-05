package hu.bp.lightrobot;

public class LightReward {
    public static double getRewardWhenLightIsSynchron(int action, boolean lightOn) {
        return (lightOn && action == 1) ? 1 : -1;
    }

    public static double getRewardWhenLightIsAsynchron(int action, boolean lightOn) {
        return (lightOn && action == 0) ? 1 : -1;
    }
}

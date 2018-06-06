package hu.bp.lightrobot;

public class LightReward {
    private static boolean lightIsSynchron(int action, boolean lightOn) {
        return (lightOn && action == 1) || (!lightOn && action == 0);
    }
    public static double getRewardWhenLightIsSynchron(int action, boolean lightOn) {
        return lightIsSynchron(action, lightOn) ? 0 : -1;
    }

    public static double getRewardWhenLightIsAsynchron(int action, boolean lightOn) {
        return lightIsSynchron(action, lightOn) ? -1 : 0;
    }
}

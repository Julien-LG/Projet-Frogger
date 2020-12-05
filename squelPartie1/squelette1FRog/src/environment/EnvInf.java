package environment;
import gameCommons.Game;
import gameCommons.IEnvironment;
import util.Case;

import java.util.ArrayList;

public class EnvInf implements IEnvironment {

    private Game game;
    private ArrayList<LaneInf> roads = new ArrayList<>();

    public EnvInf(Game game) {
        this.game = game;
        (this.roads = new ArrayList<LaneInf>()).add(new LaneInf(game, 0, 0.0));
        this.roads.add(new LaneInf(game, 1, 0.0));
        for (int i = 2; i < game.height; i++) {
            this.addRoad(i);
        }
    }

    public void addRoad(int n) {
        this.roads.add(new LaneInf(this.game, n));
    }

    @Override
    public void addRoad() {
        this.addRoad(this.roads.size());
    }

    @Override
    public boolean isSafe(Case c) {
        return this.roads.get(c.ord).isSafe(c);
    }

    @Override
    public boolean isWinningPosition(Case c) {
        return false;
    }

    @Override
    public void update() {
        for (LaneInf lane: this.roads) {
            lane.update();
        }
    }
}

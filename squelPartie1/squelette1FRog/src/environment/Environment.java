package environment;

import java.util.ArrayList;

import graphicalElements.Element;
import util.Case;
import gameCommons.Game;
import gameCommons.IEnvironment;
//import environment.Lane;

public class Environment implements IEnvironment {

    private Game game;
    private ArrayList<Lane> roads = new ArrayList<>();
    //protected ArrayList<Lane> roads = new ArrayList<>();

    public Environment(Game game){
        this.game = game;
        this.roads.add(new Lane(game, 0, 0.0));
        for (int i = 1; i < game.height - 1; i++) {
            this.roads.add(new Lane(game, i));
        }
        this.roads.add(new Lane(game, game.height -1, 0.0));
    }

    @Override
    public boolean isSafe(Case c) {
        return this.roads.get(c.ord).isSafe(c);
    }

    @Override
    public boolean isWinningPosition(Case c) {
        return c.ord == game.height - 1;
    }

    @Override
    public void update() {
        for (Lane lane: this.roads) {
            lane.update();
        }

        //game.getGraphic().test();
    }
    public ArrayList<LaneInf> getRoads() {
        return new ArrayList<LaneInf>();
    }
    public void addRoad() {

    }
}

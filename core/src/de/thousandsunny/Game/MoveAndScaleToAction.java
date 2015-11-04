package de.thousandsunny.Game;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.ScaleToAction;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class MoveAndScaleToAction extends TemporalAction{
    private MoveToAction moveToAction;
    private ScaleToAction scaleToAction;

    public MoveAndScaleToAction(){
        moveToAction = new MoveToAction();
        scaleToAction = new ScaleToAction();
    }

    public void setScale(float x){
        scaleToAction.setScale(x);
    }

    public void setPosition(float x, float y){
        moveToAction.setPosition(x, y);
    }

    public void setDuration(float duration){
        moveToAction.setDuration(duration);
        scaleToAction.setDuration(duration);
    }

    @Override
    public boolean act(float timeDelta){
        boolean rueckgabe = true;

        rueckgabe &= moveToAction.act(timeDelta);
        rueckgabe &= scaleToAction.act(timeDelta);

        return rueckgabe;
    }

    @Override
    protected void update(float percent){
    }

    @Override
    public void setActor(Actor actor){
        super.setActor(actor);
        moveToAction.setActor(actor);
        scaleToAction.setActor(actor);
    }
}

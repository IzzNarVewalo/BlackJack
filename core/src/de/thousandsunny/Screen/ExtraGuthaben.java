package de.thousandsunny.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.HashMap;
import java.util.Map;


public class ExtraGuthaben implements Screen {
    private Stage stage;
    private TextField guthabenDazu;
    private int guthaben;
    private SpriteBatch batch = new SpriteBatch();

    @Override
    public void show() {
    //skin setzen
        getOnlineGuthaben();
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        VerticalGroup verticalGroup = new VerticalGroup().space(70).pad(175).fill(0.4f);
        verticalGroup.setBounds(0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        guthabenDazu = new TextField("200", Options.skin);
        TextButton guthaben = new TextButton("Guthaben einzahlen", Options.skin);
        TextButton back = new TextButton("zum Onlinebildschirm", Options.skin);


        verticalGroup.addActor(guthabenDazu);
        verticalGroup.addActor(guthaben);
        verticalGroup.addActor(back);


        //Buttons Funktionen Zuteilen
        guthaben.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                guthabenAddieren();

            }
        });

        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new OnlineMenu());
            }
        });
        stage.addActor(verticalGroup);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        Options.font.draw(batch, "aktuelles Guthaben : " + guthaben, 412, 684);
        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }


    private void guthabenAddieren(){
        Map<String, String> einzahlung = new HashMap<>();
        int geld = Integer.parseInt(guthabenDazu.getText()) + guthaben;
        einzahlung.put("benutzername", Login.name);
        einzahlung.put("guthaben", Integer.toString(geld));
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setUrl("http://1000sunny.de/blackjack/setguthaben.php");
        request.setContent(HttpParametersUtils.convertHttpParameters(einzahlung));

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
               guthaben = (Integer.parseInt(httpResponse.getResultAsString().substring(2)));
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.log("Failed ", t.getMessage());
            }

            @Override
            public void cancelled() {

            }
        });

    }

    public void getOnlineGuthaben() {
        Map<String, String> benutzername = new HashMap<>();
        benutzername.put("benutzername", Login.name);
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setUrl("http://1000sunny.de/blackjack/getguthaben.php");
        request.setContent(HttpParametersUtils.convertHttpParameters(benutzername));

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String response = httpResponse.getResultAsString();
                guthaben = Integer.parseInt(response.substring(2));
            }

            @Override
            public void failed(Throwable t) {
            }

            @Override
            public void cancelled() {
            }
        });
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}


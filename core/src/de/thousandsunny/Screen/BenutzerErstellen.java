package de.thousandsunny.Screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.thousandsunny.Sounds.Volume;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class BenutzerErstellen implements Screen {
    private Stage stage;
    private TextField benutzername, passwort, passwortWiederholen;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        VerticalGroup verticalGroup = new VerticalGroup().space(20).pad(55).fill(0.4f);
        verticalGroup.setBounds(0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        benutzername = new TextField("", Options.skin);
        passwort = new TextField("", Options.skin);
        passwortWiederholen = new TextField("", Options.skin);
        TextButton createAccount = new TextButton("Account erstellen", Options.skin);
        TextButton back = new TextButton("zum Einloggen", Options.skin);

        passwort.setPasswordMode(true);
        passwortWiederholen.setPasswordMode(true);
        passwort.setPasswordCharacter('*');
        passwortWiederholen.setPasswordCharacter('*');

        verticalGroup.addActor(new Label("Benutzername:", Options.skin));
        verticalGroup.addActor(benutzername);
        verticalGroup.addActor(new Label("Passwort:", Options.skin));
        verticalGroup.addActor(passwort);
        verticalGroup.addActor(new Label("Passwort wiederholen:", Options.skin));
        verticalGroup.addActor(passwortWiederholen);
        verticalGroup.addActor(createAccount);
        verticalGroup.addActor(back);


        //Buttons Funktionen Zuteilen
        createAccount.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                    if (passwort.getText().equals(passwortWiederholen.getText()))
                    benutzerErstellen();
            }
        });

        back.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new Login());
            }
        });

        stage.addActor(verticalGroup);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }


    private void benutzerErstellen(){
        Map<String, String> loginData = new HashMap<>();
        loginData.put("benutzername", benutzername.getText());
        loginData.put("passwort", hashen(passwort));
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        request.setUrl("http://1000sunny.de/blackjack/create.php");
        request.setContent(HttpParametersUtils.convertHttpParameters(loginData));

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
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

    private String hashen(TextField pw){
        MessageDigest md5;
        String salted, hashed = null;
        String salt = "$K?1/S_@2%e#el!3>s#5BRo$a1+";
        salted = pw.getText() + salt;
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(salted.getBytes(), 0, salted.length());
            hashed = new BigInteger(1, md5.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (hashed.length() == 31)
            hashed = "0"+hashed;
        return hashed;
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

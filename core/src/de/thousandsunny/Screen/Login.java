package de.thousandsunny.Screen;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import de.thousandsunny.Sounds.Volume;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


public class Login implements Screen {
    private Stage stage;
    private TextField benutzername, passwort;
    private String salt = new String("$K?1/S_@2%e#el!3>s#5BRo$a1+");
    public static String name;
    protected boolean richtigesPasswort = false;

    @Override
    public void show() { //skin setzen
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        VerticalGroup verticalGroup = new VerticalGroup().space(35).pad(75).fill(0.4f);
        verticalGroup.setBounds(0, 0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        benutzername = new TextField("", Options.skin);
        passwort = new TextField("", Options.skin);
        TextButton einloggen = new TextButton("Einloggen", Options.skin);
        TextButton createAccount = new TextButton("Account erstellen", Options.skin);

        passwort.setPasswordMode(true);
        passwort.setPasswordCharacter('*');

        verticalGroup.addActor(new Label("Benutzername:", Options.skin));
        verticalGroup.addActor(benutzername);
        verticalGroup.addActor(new Label("Passwort:", Options.skin));
        verticalGroup.addActor(passwort);
        verticalGroup.addActor(einloggen);
        verticalGroup.addActor(createAccount);


        //Buttons Funktionen Zuteilen
        einloggen.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                einloggen();
                if (richtigesPasswort) {
                    name = benutzername.getText();
                    ((Game) Gdx.app.getApplicationListener()).setScreen(new OnlineMenu());
                }
            }
        });
        createAccount.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new BenutzerErstellen());
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

    //methode die eine php anfrage shickt
    private void einloggen(){
        Map<String, String> loginData = new HashMap<>();
        //Benutzerdaten die per Textfeld angefragt werde
        loginData.put("benutzername", benutzername.getText());
        loginData.put("passwort", hashen());
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.POST);
        //die url zur php seite
        request.setUrl("http://1000sunny.de/blackjack/login.php");
        request.setContent(HttpParametersUtils.convertHttpParameters(loginData));
        hashen();

        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                String response = httpResponse.getResultAsString();
                int code = Integer.parseInt(response.substring(2));
                if (code == 20)
                    richtigesPasswort = true;
                else
                if (!richtigesPasswort)
                Volume.chip.play(0.6f);
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

    //methode um das passwort inklusive salt zu hashen (md5)
    private String hashen(){
        MessageDigest md5;
        String salted, hashed = null;
        //fügt dem eingegebenen passwort den salt dazu
        salted = passwort.getText() + salt;
        try {
            md5 = MessageDigest.getInstance("MD5");
            //salt = $K?1/S_@2%e#el!3>s#5BRo$a1+
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

package pt.ipleiria.estg.dei.ei.es2.bio;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.robotium.solo.Solo;

import java.util.BitSet;

import javax.xml.transform.SourceLocator;

/**
 * Created by Bruno on 05-03-2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }


////////////////////////////////////////////////////////////////////////////

    public void testMensagemConfirmacao() throws Exception {

//       desbloqueia o ecra
        solo.unlockScreen();

        setContactData("bruno", "bruno@email.com");

//        escolhe o icone
        solo.clickOnActionBarItem(R.id.action_checkin);

//    1º teste - confirma que apanha o texto
        assertTrue(solo.searchText(getActivity().getString(R.string.titleConfirmation)));


    }

    private void setContactData(String name, String email) {

        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();

        editor.putString("NAME", name);
        editor.putString("EMAIL", email);

        editor.commit();

    }


////////////////////////////////////////////////////////////////////////////

//    2º teste

    public void testCheckin() throws Exception {
        solo.unlockScreen();
        solo.clickOnActionBarItem(R.id.action_checkin);

//        escolher sim
        solo.clickOnButton(getActivity().getString(R.string.okBtn));

//        espera pelo inicio de nova activity
        solo.waitForActivity(ParticipantsListActivity.class);

//        confirma que muda de activity
        solo.assertCurrentActivity("Não passei para a actividade com" + "lista de participantes", ParticipantsListActivity.class);

        boolean obtainedResult = verifyIcon(R.drawable.ic_action_name2);

        assertTrue("Icon não é o de checkin", obtainedResult);

    }

    //    método auxiliar para comparar icones da actionbar
    private boolean verifyIcon(int icon) {

//        Necessário para aceder à action bar; é uma label da action bar a partir da qual se obtem a imagem
//        está a dar erro e o solo n encontra o icone
//        TextView v = (TextView) solo.getCurrentActivity().findViewById(R.id.action_checkin);

        //alternativa
        TextView v = (TextView) getActivity().findViewById(R.id.action_checkin);

//        debug para verificar se encontra o icone
        assertNotNull("item menu não encontrado", v);

//        obter a imagem em bitmap pois o objecto pode ser diferfente devido a diferentes res
        Bitmap actual = ((BitmapDrawable) v.getCompoundDrawables()[0]).getBitmap();

//        aquele que esperamos que lá esteja, que é passado por parametro
        Bitmap expected = ((BitmapDrawable) solo.getCurrentActivity().getResources().getDrawable(icon)).getBitmap();

//        devolve resultado da comparação entre icon atual e o expected
        return expected.sameAs(actual);
    }


}

package com.msteber.kelimegezmeceyardim;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.msteber.kelimegezmeceyardim.database.DataBaseCopyHelper;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, IDegisiklikBildir {
    private ImageButton buttonAra, buttonSil, buttonMenu;
    private EditText editTextGiris;
    public static String editTextKelime;
    public static ArrayList<String> ucHarfliKelimelerSonuc, dortHarfliKelimelerSonuc, besHarfliKelimelerSonuc, altiHarfliKelimelerSonuc, yediHarfliKelimelerSonuc, sekizHarfliKelimelerSonuc;
    public static boolean ucharfCheckbox, dortharfCheckbox, besharfCheckbox, altiharfCheckbox, yediharfCheckbox, sekizharfCheckbox, serviceBaslatma; //menüdeki checkboxların işaretli olup olmama durumunun kontrolü için gerekli boolean değişkenler.
    private int menuItemNo;  //bkz: menuItemBelirle method açıklaması.
    private PopupMenu popupMenu;
    public LinearLayout myLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ucharfCheckbox = false;
        dortharfCheckbox = false;
        besharfCheckbox = false;
        altiharfCheckbox = false;
        yediharfCheckbox = false;
        sekizharfCheckbox = false;
        serviceBaslatma = true;
        menuItemNo = 0;

        buttonAra = findViewById(R.id.ButtonAra);
        buttonSil = findViewById(R.id.ButtonSil);
        buttonMenu = findViewById(R.id.ButtonMenu);
        editTextGiris = findViewById(R.id.EditTextGiris);
        myLinearLayout = findViewById(R.id.myLinearLayout);


        popupMenu = new PopupMenu(MainActivity.this, buttonMenu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_kacharf, popupMenu.getMenu());

        veriTabaniKopyala();

        editTextListener();
        buttonSil.setOnClickListener(this);
        buttonAra.setOnClickListener(this);
        buttonMenu.setOnClickListener(this);


    }

    //IDegisiklikBildir interface'inin methodları; goster() methodu asynctask sınıfının işlem bittikten sonra çalıştırılan postexecute methodundan
    // tetiklenerek elde edilen sonuçları textviewlar halinde ekrana eklemek suretiyle UI'ı değiştiriyor.
    @Override
    public void goster() {
        if ((ucHarfliKelimelerSonuc == null || ucHarfliKelimelerSonuc.isEmpty()) && (dortHarfliKelimelerSonuc == null || dortHarfliKelimelerSonuc.isEmpty()) && (besHarfliKelimelerSonuc == null || besHarfliKelimelerSonuc.isEmpty()) && (altiHarfliKelimelerSonuc == null || altiHarfliKelimelerSonuc.isEmpty()) && (yediHarfliKelimelerSonuc == null || yediHarfliKelimelerSonuc.isEmpty()) && (sekizHarfliKelimelerSonuc == null || sekizHarfliKelimelerSonuc.isEmpty())) {
            Toast.makeText(this, "Eşleşen sonuc bulunamadı", Toast.LENGTH_SHORT).show();
        }else{
            textViewEkle();
        }
    }

    //Asynctask sınıfının içinde ihtiyaç duyulan Context nesneleri contextVer methodu çağrılarak elde ediliyor.
    @Override
    public Context contextVer() {
        return MainActivity.this;
    }


    //Edittextin listenerı.
    private void editTextListener() {
        //Edittextte yapılan değişiklikleri dinliyor.
        editTextGiris.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                gorunurlukButtonSil();
                menuCheckboxTemizleme(count);
                editTextKelime = editTextGiris.getText().toString().toLowerCase();

                //Kombinasyonlu kelimeler karşılaştırılırken dönen her sonuc arrayListini arka arkaya eklemede kullanılan addAll() methodu kullanılıyor.
                // Burada edittextten alınan girdi değiştikçe sonuç arrayListleri sıfırlanıyor ki önceki girdinin sonuçları sonraki girdinin sonuçlarına eklenmesin.
                sonucArrayListSifirlama();
                myLinearLayout.removeAllViewsInLayout();
            }

            @Override
            public void afterTextChanged(Editable s) {
                gorunurlukButtonSil();
            }
        });
    }

    //Uygulamadaki butonların ortak onClick methodu.
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ButtonAra:
                sonucArrayListSifirlama();

                if (editTextKelime != null && editTextKelime.length() >= 3) {
                    if(popupUyari()){
                        if(serviceBaslatma){
                            //Intent serviceIntent = new Intent(MainActivity.this, MyService.class);
                            //startService(serviceIntent);
                            MyAsyncTaskClass myAsyncTaskClass = new MyAsyncTaskClass(this);
                            myAsyncTaskClass.execute();
                        }
                    }
                } else {
                    Toast.makeText(this, "Lütfen en az 3 harf giriniz", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ButtonSil:
                editTextGiris.setText("");
                break;
            case R.id.ButtonMenu:
                popupDurumKoruma(popupMenu);
                popupMenu.show();
                popupOnClick(popupMenu);
                break;
        }
    }

    //Popup menünün onClick methodu.
    private void popupOnClick(PopupMenu popup) {
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                menuItemNo = menuItemBelirle(item);
                if(editTextKelime != null){
                    if (editTextKelime.length() > menuItemNo || editTextKelime.length() == menuItemNo) {
                        //Tıklanan item işaretlenmişse işareti kaldırıyor, işaretlenmemişse işaretliyor.
                        item.setChecked(!item.isChecked());
                    } else if (editTextKelime.length() < 3) {
                        Toast.makeText(MainActivity.this, "Lütfen en az 3 harf giriniz", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, "Girdiğiniz harf sayısından fazla harf seçemezsiniz", Toast.LENGTH_SHORT).show();
                        if (item.isChecked()) {
                            item.setChecked(false);
                        }
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Lütfen en az 3 harf giriniz", Toast.LENGTH_SHORT).show();
                }


                //Hangi itemin tıklandığını id ler vasıtasıyla tespit edip ona göre tıklanan itemle ilgili işlem yapıyor.
                switch (item.getItemId()) {
                    case R.id.ucharf_checkbox:
                        ucharfCheckbox = item.isChecked();
                        break;
                    case R.id.dortharf_checkbox:
                        dortharfCheckbox = item.isChecked();
                        break;
                    case R.id.besharf_checkbox:
                        besharfCheckbox = item.isChecked();
                        break;
                    case R.id.altiharf_checkbox:
                        altiharfCheckbox = item.isChecked();
                        break;
                    case R.id.yediharf_checkbox:
                        yediharfCheckbox = item.isChecked();
                        break;
                    case R.id.sekizharf_checkbox:
                        sekizharfCheckbox = item.isChecked();
                        break;
                }

                //Popup menüyü iteme tıklanma olayından sonra da açık tutuyor. (alıntı: stackoverflow)
                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
                item.setActionView(new View(getApplicationContext()));
                item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return false;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        return false;
                    }
                });
                return false;
            }
        });
    }

    //Uygulamayla birlikte gelen veritabanını uygulamanın cihazda ilk çalıştırıldığı anda cihaza kopyalıyor.
    private void veriTabaniKopyala() {
        DataBaseCopyHelper helper = new DataBaseCopyHelper(this);

        try {
            helper.createDataBase();
        } catch (Exception e) {
            Toast.makeText(this, "Veritabanı Kopyalama Hatası", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        helper.openDataBase();
    }

    //Edittextin boş olup olmaması koşuluna buttonSilin görünürlüğünü ayarlıyor.
    private void gorunurlukButtonSil() {
        String s = editTextGiris.getText().toString();
        if (!(s.matches(""))) {
            buttonSil.setVisibility(View.VISIBLE);
        } else {
            buttonSil.setVisibility(View.INVISIBLE);
        }
    }

    //Menüdeki checkboxların işaretlenme durumunun menünün kapatılıp açılmasından sonra da aynı kalmasını sağlıyor.
    //(kullanıldığı yer: OnClick > case ButtonMenu)
    private void popupDurumKoruma(PopupMenu popupMenuDurum) {
        if (ucharfCheckbox) {
            popupMenuDurum.getMenu().findItem(R.id.ucharf_checkbox).setChecked(true);
        }
        if (dortharfCheckbox) {
            popupMenuDurum.getMenu().findItem(R.id.dortharf_checkbox).setChecked(true);
        }
        if (besharfCheckbox) {
            popupMenuDurum.getMenu().findItem(R.id.besharf_checkbox).setChecked(true);
        }
        if (altiharfCheckbox) {
            popupMenuDurum.getMenu().findItem(R.id.altiharf_checkbox).setChecked(true);
        }
        if (yediharfCheckbox) {
            popupMenuDurum.getMenu().findItem(R.id.yediharf_checkbox).setChecked(true);
        }
        if (sekizharfCheckbox) {
            popupMenuDurum.getMenu().findItem(R.id.sekizharf_checkbox).setChecked(true);
        }
    }

    //Popup menüdeki itemların tıklanma olayına bağlı olarak integer bir değer döndürüyor.
    //Bu değer edittextten girilen harf sayısıyla karşılaştırılmak için kullanılıyor.
    //(kullanıldığı yer: popupOnClick > onMenuItemClick)
    private int menuItemBelirle(MenuItem menuItem) {
        int itemNo = 0;
        switch (menuItem.getItemId()) {
            case R.id.ucharf_checkbox:
                itemNo = 3;
                break;
            case R.id.dortharf_checkbox:
                itemNo = 4;
                break;
            case R.id.besharf_checkbox:
                itemNo = 5;
                break;
            case R.id.altiharf_checkbox:
                itemNo = 6;
                break;
            case R.id.yediharf_checkbox:
                itemNo = 7;
                break;
            case R.id.sekizharf_checkbox:
                itemNo = 8;
                break;
        }
        return itemNo;
    }

    //Edittexte girilen harf sayısına göre popup menüdeki checkboxların işaretlenme durumlarına bakarak işaretlemeri kaldırıyor.
    //Örneğin; kullanıcı 4 harf girmiş ardından popuptan 3 ve 4 harf seçeneklerini seçmiş ama daha sonra edittextten bir harf silmişse
    //edittextteki bu değişiklik kontrol edilip 3 harf olduğu anlaşıldığında popuptaki 4 harf seçeneği false durumuna getiriliyor.
    //(kullanıldığı yer: editTextListener > onTextChanged)
    private void menuCheckboxTemizleme(int editTextHarfSayisi) {

        switch (editTextHarfSayisi) {
            case 3:
                //popupMenu.getMenu().findItem(R.id.ucharf_checkbox).setChecked(true);
                //ucharfCheckbox = true;

                if (popupMenu.getMenu().findItem(R.id.dortharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.dortharf_checkbox).setChecked(false);
                    dortharfCheckbox = false;
                }
                if (popupMenu.getMenu().findItem(R.id.besharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.besharf_checkbox).setChecked(false);
                    besharfCheckbox = false;
                }
                if (popupMenu.getMenu().findItem(R.id.altiharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.altiharf_checkbox).setChecked(false);
                    altiharfCheckbox = false;
                }
                if (popupMenu.getMenu().findItem(R.id.yediharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.yediharf_checkbox).setChecked(false);
                    yediharfCheckbox = false;
                }
                if (popupMenu.getMenu().findItem(R.id.sekizharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.sekizharf_checkbox).setChecked(false);
                    sekizharfCheckbox = false;
                }
                break;
            case 4:
                if (popupMenu.getMenu().findItem(R.id.besharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.besharf_checkbox).setChecked(false);
                    besharfCheckbox = false;
                }
                if (popupMenu.getMenu().findItem(R.id.altiharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.altiharf_checkbox).setChecked(false);
                    altiharfCheckbox = false;
                }
                if (popupMenu.getMenu().findItem(R.id.yediharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.yediharf_checkbox).setChecked(false);
                    yediharfCheckbox = false;
                }
                if (popupMenu.getMenu().findItem(R.id.sekizharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.sekizharf_checkbox).setChecked(false);
                    sekizharfCheckbox = false;
                }
                break;
            case 5:
                if (popupMenu.getMenu().findItem(R.id.altiharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.altiharf_checkbox).setChecked(false);
                    altiharfCheckbox = false;
                }
                if (popupMenu.getMenu().findItem(R.id.yediharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.yediharf_checkbox).setChecked(false);
                    yediharfCheckbox = false;
                }
                if (popupMenu.getMenu().findItem(R.id.sekizharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.sekizharf_checkbox).setChecked(false);
                    sekizharfCheckbox = false;
                }
                break;
            case 6:
                if (popupMenu.getMenu().findItem(R.id.yediharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.yediharf_checkbox).setChecked(false);
                    yediharfCheckbox = false;
                }
                if (popupMenu.getMenu().findItem(R.id.sekizharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.sekizharf_checkbox).setChecked(false);
                    sekizharfCheckbox = false;
                }
                break;
            case 7:
                if (popupMenu.getMenu().findItem(R.id.sekizharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.sekizharf_checkbox).setChecked(false);
                    sekizharfCheckbox = false;
                }
                break;
            case 8:
                break;
            default:

                if (popupMenu.getMenu().findItem(R.id.ucharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.ucharf_checkbox).setChecked(false);
                    ucharfCheckbox = false;
                }
                if (popupMenu.getMenu().findItem(R.id.dortharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.dortharf_checkbox).setChecked(false);
                    dortharfCheckbox = false;
                }
                if (popupMenu.getMenu().findItem(R.id.besharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.besharf_checkbox).setChecked(false);
                    besharfCheckbox = false;
                }
                if (popupMenu.getMenu().findItem(R.id.altiharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.altiharf_checkbox).setChecked(false);
                    altiharfCheckbox = false;
                }
                if (popupMenu.getMenu().findItem(R.id.yediharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.yediharf_checkbox).setChecked(false);
                    yediharfCheckbox = false;
                }
                if (popupMenu.getMenu().findItem(R.id.sekizharf_checkbox).isChecked()) {
                    popupMenu.getMenu().findItem(R.id.sekizharf_checkbox).setChecked(false);
                    sekizharfCheckbox = false;
                }
                break;
        }
    }

    //İhtiyaç halinde arraylistlerin null'a eşitlenmesini sağlıyor.
    private void sonucArrayListSifirlama() {
        ucHarfliKelimelerSonuc = null;
        dortHarfliKelimelerSonuc = null;
        besHarfliKelimelerSonuc = null;
        altiHarfliKelimelerSonuc = null;
        yediHarfliKelimelerSonuc = null;
        sekizHarfliKelimelerSonuc = null;
    }

    //Popup menudeki itemlardan herhangi birisinin seçili olup olmadığını kontrol edip ona göre true veya false değeri döndürüyor.
    //Hiç bir item seçili değilse uyarı vererek popup menüyü açarak kullanıcıyı seçim yapmaya yönlendiriyor.
    //OnClick methodunda buttonAra butonunun tıklanma durumunda kullanılıyor.
    private boolean popupUyari() {
        boolean donut = false;

        if (ucharfCheckbox || dortharfCheckbox || besharfCheckbox || altiharfCheckbox || yediharfCheckbox || sekizharfCheckbox) {
            donut = true;
        } else {
            popupMenu.show();
            Toast.makeText(this, "Lütfen oluşturmak istediğiniz kelimeler için harf sayısı seçin!", Toast.LENGTH_LONG).show();
        }
       
        return donut;
    }
    //Eşleşen kelimelerin veritabanından seçilerek ilgili arraylist'e aktarılmasından sonra, arraylistte olası tekrarları önlemeye yarıyor.
    //Tekrar varsa arraylistten siliyor.
    //textViewEkle() methodunda kullanılıyor.
    private void ayniKelimeAyiklama(ArrayList<String> ayiklanacakArrayList) {
        for (int i = 0; i < ayiklanacakArrayList.size(); i++) {
            for (int j = i + 1; j < ayiklanacakArrayList.size(); ) {
                if (ayiklanacakArrayList.get(i).equals(ayiklanacakArrayList.get(j))) {
                    ayiklanacakArrayList.remove(j);
                } else {
                    j++;
                }
            }
        }
    }

    //Elde edilen kelimeleri textview'lar oluşturarak linearlayout'a ekliyor.
    //IDegisiklikBildir interface'inin goster() methodunda kullanılıyor.
    private void textViewEkle() {
        myLinearLayout.removeAllViewsInLayout();
        if (ucHarfliKelimelerSonuc != null) {

            for (int i = 0; i < ucHarfliKelimelerSonuc.size(); i++) {
                TextView linearLayoutTextView = new TextView(this);
                linearLayoutTextView.setText(ucHarfliKelimelerSonuc.get(i));
                linearLayoutTextView.setTextSize(23);
                linearLayoutTextView.setTypeface(Typeface.DEFAULT_BOLD);
                myLinearLayout.addView(linearLayoutTextView);
            }
        }

        if (dortHarfliKelimelerSonuc != null) {

            for (int i = 0; i < dortHarfliKelimelerSonuc.size(); i++) {
                TextView linearLayoutTextView = new TextView(this);
                linearLayoutTextView.setText(dortHarfliKelimelerSonuc.get(i));
                linearLayoutTextView.setTextSize(23);
                linearLayoutTextView.setTypeface(Typeface.DEFAULT_BOLD);
                myLinearLayout.addView(linearLayoutTextView);
            }
        }

        if (besHarfliKelimelerSonuc != null) {

            for (int i = 0; i < besHarfliKelimelerSonuc.size(); i++) {
                TextView linearLayoutTextView = new TextView(this);
                linearLayoutTextView.setText(besHarfliKelimelerSonuc.get(i));
                linearLayoutTextView.setTextSize(23);
                linearLayoutTextView.setTypeface(Typeface.DEFAULT_BOLD);
                myLinearLayout.addView(linearLayoutTextView);
            }
        }

        if (altiHarfliKelimelerSonuc != null) {

            for (int i = 0; i < altiHarfliKelimelerSonuc.size(); i++) {
                TextView linearLayoutTextView = new TextView(this);
                linearLayoutTextView.setText(altiHarfliKelimelerSonuc.get(i));
                linearLayoutTextView.setTextSize(23);
                linearLayoutTextView.setTypeface(Typeface.DEFAULT_BOLD);
                myLinearLayout.addView(linearLayoutTextView);
            }
        }

        if (yediHarfliKelimelerSonuc != null) {

            for (int i = 0; i < yediHarfliKelimelerSonuc.size(); i++) {
                TextView linearLayoutTextView = new TextView(this);
                linearLayoutTextView.setText(yediHarfliKelimelerSonuc.get(i));
                linearLayoutTextView.setTextSize(23);
                linearLayoutTextView.setTypeface(Typeface.DEFAULT_BOLD);
                myLinearLayout.addView(linearLayoutTextView);
            }
        }

        if (sekizHarfliKelimelerSonuc != null) {

            for (int i = 0; i < sekizHarfliKelimelerSonuc.size(); i++) {
                TextView linearLayoutTextView = new TextView(this);
                linearLayoutTextView.setText(sekizHarfliKelimelerSonuc.get(i));
                linearLayoutTextView.setTextSize(23);
                linearLayoutTextView.setTypeface(Typeface.DEFAULT_BOLD);
                myLinearLayout.addView(linearLayoutTextView);
            }
        }
    }

}

package com.arcanix.messenger;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

public class MainActivity extends Activity {
    private final int bg = Color.rgb(10, 12, 29);
    private final int panel = Color.rgb(20, 23, 48);
    private final int purple = Color.rgb(111, 76, 210);
    private final int light = Color.rgb(241, 242, 255);
    private final int muted = Color.rgb(160, 165, 195);
    private LinearLayout root, content;
    private String active = "Чаты";

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        getWindow().setStatusBarColor(bg);
        getWindow().setNavigationBarColor(bg);
        getWindow().getDecorView().setSystemUiVisibility(0);
        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(bg);
        setContentView(root);
        showPage("Чаты");
    }

    private GradientDrawable shape(int color, int radius) {
        GradientDrawable d = new GradientDrawable();
        d.setColor(color); d.setCornerRadius(radius);
        return d;
    }

    private TextView text(String value, int size, int color, boolean bold) {
        TextView t = new TextView(this);
        t.setText(value); t.setTextSize(size); t.setTextColor(color);
        if (bold) t.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        return t;
    }

    private void showPage(String page) {
        active = page;
        root.removeAllViews();
        LinearLayout header = new LinearLayout(this);
        header.setPadding(20, 18, 18, 16);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setBackgroundColor(bg);
        TextView logo = text("◈", 30, purple, true);
        header.addView(logo);
        TextView title = text(" ARCANIX", 23, light, true);
        header.addView(title);
        View spacer = new View(this);
        header.addView(spacer, new LinearLayout.LayoutParams(0, 1, 1));
        TextView search = text("⌕", 30, light, false);
        search.setGravity(Gravity.CENTER);
        header.addView(search, new LinearLayout.LayoutParams(42, 44));
        root.addView(header);

        if (page.equals("Чаты")) {
            TextView label = text("Сообщения", 25, light, true);
            label.setPadding(20, 10, 20, 8);
            root.addView(label);
            TextView hint = text("Ваши последние диалоги", 13, muted, false);
            hint.setPadding(20, 0, 20, 16);
            root.addView(hint);
        }
        ScrollView scroll = new ScrollView(this);
        content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setPadding(12, 4, 12, 12);
        scroll.addView(content);
        root.addView(scroll, new LinearLayout.LayoutParams(-1, 0, 1));

        if (page.equals("Чаты")) buildChats();
        else if (page.equals("Контакты")) buildContacts();
        else buildSettings();

        LinearLayout nav = new LinearLayout(this);
        nav.setPadding(6, 8, 6, 8);
        nav.setBackgroundColor(panel);
        String[] pages = {"Чаты", "Контакты", "Настройки"};
        String[] icons = {"▤", "♙", "⚙"};
        for (int i=0; i<pages.length; i++) {
            final String p = pages[i];
            LinearLayout item = new LinearLayout(this);
            item.setOrientation(LinearLayout.VERTICAL);
            item.setGravity(Gravity.CENTER);
            TextView icon = text(icons[i], 22, active.equals(p) ? purple : muted, true);
            TextView name = text(p, 11, active.equals(p) ? light : muted, active.equals(p));
            item.addView(icon); item.addView(name);
            item.setOnClickListener(v -> showPage(p));
            nav.addView(item, new LinearLayout.LayoutParams(0, 54, 1));
        }
        root.addView(nav);
    }

    private void buildChats() {
        String[][] data = {
            {"А","Александр","Привет! Как дела?","12:42","2"},
            {"М","Мария","Отправила фотографию","11:58",""},
            {"G","Gaming Team","Сегодня играем вечером?","10:21","5"},
            {"И","Илья","Окей, договорились","Вчера",""},
            {"A","ARCANIX","Добро пожаловать в ARCANIX","Вчера",""}
        };
        for (String[] row : data) {
            LinearLayout card = new LinearLayout(this);
            card.setGravity(Gravity.CENTER_VERTICAL);
            card.setPadding(10, 12, 10, 12);
            card.setBackground(shape(panel, 20));
            LinearLayout.LayoutParams cp = new LinearLayout.LayoutParams(-1, -2);
            cp.setMargins(0, 5, 0, 5);
            card.setLayoutParams(cp);

            TextView avatar = text(row[0], 20, Color.WHITE, true);
            avatar.setGravity(Gravity.CENTER);
            avatar.setBackground(shape(purple, 60));
            card.addView(avatar, new LinearLayout.LayoutParams(48, 48));

            LinearLayout info = new LinearLayout(this);
            info.setOrientation(LinearLayout.VERTICAL);
            info.setPadding(12, 0, 4, 0);
            TextView name = text(row[1], 16, light, true);
            TextView msg = text(row[2], 13, muted, false);
            info.addView(name); info.addView(msg);
            card.addView(info, new LinearLayout.LayoutParams(0, -2, 1));
            LinearLayout right = new LinearLayout(this);
            right.setOrientation(LinearLayout.VERTICAL);
            right.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            right.addView(text(row[3], 11, muted, false));
            if (!row[4].isEmpty()) {
                TextView badge = text(row[4], 11, Color.WHITE, true);
                badge.setGravity(Gravity.CENTER);
                badge.setBackground(shape(purple, 40));
                LinearLayout.LayoutParams bp = new LinearLayout.LayoutParams(24, 24);
                bp.topMargin = 5; right.addView(badge, bp);
            }
            card.addView(right);
            card.setOnClickListener(v -> openChat(row[1]));
            content.addView(card);
        }
        TextView note = text("Демонстрационный интерфейс • сообщения пока не отправляются", 11, muted, false);
        note.setGravity(Gravity.CENTER);
        note.setPadding(8, 18, 8, 12);
        content.addView(note);
    }

    private void buildContacts() {
        TextView heading = text("Контакты", 24, light, true);
        heading.setPadding(8, 12, 8, 16); content.addView(heading);
        String[] names = {"Александр", "Мария", "Gaming Team", "Илья"};
        for (String name : names) {
            TextView row = text("●   " + name + "                         ›", 16, light, false);
            row.setPadding(16, 18, 12, 18);
            row.setBackground(shape(panel, 16));
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
            p.setMargins(0, 5, 0, 5); content.addView(row, p);
            row.setOnClickListener(v -> openChat(name));
        }
    }

    private void buildSettings() {
        TextView profile = text("◈", 44, Color.WHITE, true);
        profile.setGravity(Gravity.CENTER);
        profile.setBackground(shape(purple, 100));
        LinearLayout.LayoutParams ap = new LinearLayout.LayoutParams(86, 86);
        ap.gravity = Gravity.CENTER; ap.topMargin = 24; ap.bottomMargin = 12;
        content.addView(profile, ap);
        TextView name = text("Пользователь ARCANIX", 20, light, true);
        name.setGravity(Gravity.CENTER); content.addView(name);
        TextView user = text("@arcanix_user", 14, muted, false);
        user.setGravity(Gravity.CENTER); user.setPadding(0, 4, 0, 24); content.addView(user);
        String[] rows = {"Изменить профиль", "Уведомления", "Конфиденциальность", "Оформление: тёмно-фиолетовое", "О приложении ARCANIX", "Версия 1.0"};
        for (String s : rows) {
            TextView r = text("  " + s + "     ›", 15, light, false);
            r.setPadding(12, 17, 12, 17); r.setBackground(shape(panel, 14));
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
            p.setMargins(0, 4, 0, 4); content.addView(r, p);
        }
    }

    private void openChat(String name) {
        root.removeAllViews();
        LinearLayout header = new LinearLayout(this);
        header.setPadding(16, 18, 16, 18);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setBackgroundColor(panel);
        TextView back = text("‹", 32, light, true);
        header.addView(back);
        TextView title = text("   " + name, 19, light, true);
        header.addView(title, new LinearLayout.LayoutParams(-1, -2));
        back.setOnClickListener(v -> showPage("Чаты"));
        root.addView(header);

        LinearLayout messages = new LinearLayout(this);
        messages.setOrientation(LinearLayout.VERTICAL);
        messages.setPadding(14, 18, 14, 18);
        messages.setBackgroundColor(bg);
        TextView intro = text("Это начало вашей переписки с «" + name + "»", 13, muted, false);
        intro.setGravity(Gravity.CENTER); intro.setPadding(4, 16, 4, 16);
        messages.addView(intro);
        addBubble(messages, "Привет! Добро пожаловать в ARCANIX 👋", false);
        addBubble(messages, "Спасибо! Выглядит круто.", true);
        addBubble(messages, "Это демонстрационный чат", false);
        View spacer = new View(this);
        messages.addView(spacer, new LinearLayout.LayoutParams(1, 0, 1));
        ScrollView sc = new ScrollView(this); sc.addView(messages);
        root.addView(sc, new LinearLayout.LayoutParams(-1, 0, 1));

        LinearLayout composer = new LinearLayout(this);
        composer.setPadding(10, 8, 10, 8); composer.setGravity(Gravity.CENTER_VERTICAL);
        composer.setBackgroundColor(panel);
        EditText input = new EditText(this);
        input.setSingleLine(true); input.setHint("Сообщение...");
        input.setHintTextColor(muted); input.setTextColor(light);
        input.setBackground(shape(bg, 28)); input.setPadding(16, 8, 16, 8);
        composer.addView(input, new LinearLayout.LayoutParams(0, 46, 1));
        TextView send = text("➤", 22, Color.WHITE, true);
        send.setGravity(Gravity.CENTER); send.setBackground(shape(purple, 50));
        LinearLayout.LayoutParams sp = new LinearLayout.LayoutParams(46, 46);
        sp.leftMargin = 8; composer.addView(send, sp);
        send.setOnClickListener(v -> {
            String value = input.getText().toString().trim();
            if (!value.isEmpty()) {
                addBubble(messages, value, true);
                input.setText("");
                sc.post(() -> sc.fullScroll(View.FOCUS_DOWN));
            }
        });
        root.addView(composer);
    }

    private void addBubble(LinearLayout messages, String value, boolean mine) {
        TextView bubble = text(value, 15, light, false);
        bubble.setPadding(14, 10, 14, 10);
        bubble.setBackground(shape(mine ? purple : panel, 18));
        LinearLayout line = new LinearLayout(this);
        line.setGravity(mine ? Gravity.RIGHT : Gravity.LEFT);
        line.addView(bubble);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(-1, -2);
        p.setMargins(0, 5, 0, 5);
        messages.addView(line, p);
    }
}

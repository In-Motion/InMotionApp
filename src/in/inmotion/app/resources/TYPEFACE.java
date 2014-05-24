package in.inmotion.app.resources;

import android.content.Context;
import android.graphics.Typeface;

public class TYPEFACE {
	public static Typeface LexiaBold(Context context) {
		Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/Lexia_Std_Bd.ttf");
		return typeface;
	}
}

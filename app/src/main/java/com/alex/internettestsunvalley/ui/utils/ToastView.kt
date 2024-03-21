import android.graphics.Color
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast

object ToastView {
    fun createNewToast(view: View, message: String, textColor: Int, backgroundColor: Int, borderColor: Int, borderWidth: Int) {
        val toast = Toast.makeText(view.context, "", Toast.LENGTH_SHORT)
        val spannableString = SpannableString(message)
        spannableString.setSpan(ForegroundColorSpan(textColor), 0, message.length, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

        val borderDrawable = ShapeDrawable(RectShape())
        borderDrawable.paint.color = borderColor
        borderDrawable.paint.style = android.graphics.Paint.Style.STROKE
        borderDrawable.paint.strokeWidth = borderWidth.toFloat()

        val textView = TextView(view.context)
        textView.text = spannableString
        textView.setTextColor(textColor)
        textView.setBackgroundColor(backgroundColor)
        textView.background = borderDrawable
        textView.gravity = Gravity.CENTER
        textView.setPadding(16, 8, 16, 8) // Ajustar el padding si es necesario

        // Establecer el TextView personalizado como vista del Toast
        toast.view = textView

        // Mostrar el Toast
        toast.show()
    }
}

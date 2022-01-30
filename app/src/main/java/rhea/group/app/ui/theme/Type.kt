package rhea.group.app.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import rhea.group.app.R

val FtpPolar = FontFamily(
    Font(R.font.ftp_polar_regular, FontWeight.Normal),
    Font(R.font.ftp_polar_black, FontWeight.Black),
    Font(R.font.ftp_polar_bold, FontWeight.Bold),
    Font(R.font.ftp_polar_light, FontWeight.Light),
    Font(R.font.ftp_polar_medium, FontWeight.Medium),
    Font(R.font.ftp_polar_semibold, FontWeight.SemiBold),
    Font(R.font.ftp_polar_thin, FontWeight.Thin)
)

val KansasNew = FontFamily(
    Font(R.font.kansasnew_regular, FontWeight.Normal),
    Font(R.font.kansasnew_black, FontWeight.Black),
    Font(R.font.kansasnew_bold, FontWeight.Bold),
    Font(R.font.kansasnew_light, FontWeight.Light),
    Font(R.font.kansasnew_medium, FontWeight.Medium),
    Font(R.font.kansasnew_semibold, FontWeight.SemiBold),
    Font(R.font.kansasnew_thin, FontWeight.Thin)
)

val Typography = Typography(
    defaultFontFamily = KansasNew,
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = White
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 28.sp,
        textAlign = TextAlign.Start,
        color = White
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 30.sp,
        textAlign = TextAlign.Center,
        color = White
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 38.sp,
        textAlign = TextAlign.Center,
        color = White
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 50.sp,
        textAlign = TextAlign.Center,
        color = White
    ),
    h1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 50.sp,
        lineHeight = 56.sp,
        textAlign = TextAlign.Start,
        color = White
    ),
    button = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 26.sp,
        textAlign = TextAlign.Center,
        color = White
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 26.sp,
        textAlign = TextAlign.Start,
        color = Hoki
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        textAlign = TextAlign.Center,
        color = White
    ),
    overline = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 10.sp,
        lineHeight = 20.sp,
        textAlign = TextAlign.Center,
        color = Black
    )
)
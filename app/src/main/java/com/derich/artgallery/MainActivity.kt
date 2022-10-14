package com.derich.artgallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.derich.artgallery.ui.theme.ArtGalleryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtGalleryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ArtGalleryUI()
                }
            }
        }
    }
}
@Preview
@Composable
fun ArtGalleryUI() {
    var imageId by remember {mutableStateOf(1)}
    //use remember to maintain the buttons statuses
    var prevEnabled by remember{mutableStateOf(false)}
    var nextEnabled by remember{mutableStateOf(true)}
    val imageResourceId = when(imageId) {
        1 -> R.drawable.my_passport
        2 -> R.drawable.painting_blank
        3 -> R.drawable.painting_grafitti
        4 -> R.drawable.on_top_of_a_car
        5 -> R.drawable.painting_unedited
        6 -> R.drawable.posing_with_mask
        else -> R.drawable.sunset_field

    }
    val contentDescription = when(imageId) {
        1 -> stringResource(R.string.passport_image_description)
        2 -> stringResource(R.string.picture_of_me_painting_nothing_description)
        3 -> stringResource(R.string.picture_of_me_painting_graffiti_description)
        4 -> stringResource(R.string.chilling_on_top_of_car_description)
        5 -> stringResource(R.string.unedited_picture_of_myself_description)
        6 -> stringResource(R.string.posing_wearing_a_mask_description)
        else -> stringResource(R.string.watching_sunset_description)
    }
    val photographer = when(imageId) {
        1 -> stringResource(R.string.lawren_photography)
        2 -> stringResource(R.string.alan_gitonga)
        3 -> stringResource(R.string.alan_gitonga)
        4 -> stringResource(R.string.alan_gitonga)
        5 -> stringResource(R.string.lawren_photography)
        6 -> stringResource(R.string.lawren_photography)
        else -> stringResource(R.string.lawren_photography)
    }
    val yearTaken = when(imageId) {
        1 -> stringResource(R.string.year_2019)
        2 -> stringResource(R.string.year_2020)
        3 -> stringResource(R.string.year_2019)
        4 -> stringResource(R.string.year_2019)
        5 -> stringResource(R.string.year_2020)
        6 -> stringResource(R.string.year_2019)
        else -> stringResource(R.string.year_2021)

    }
    //functionality for the previous button.
    val onClickPrev :(Int) -> Unit = {
        if (imageId in 3..7){
            imageId--
            prevEnabled = true
            nextEnabled = true
        }
        else if (imageId == 2){
            imageId--
            prevEnabled = false
        }
    }
    //adding functionality to next button
    val onClickNext: (Int) -> Unit = {
        when(imageId){
            in 1..5 -> {
                imageId++
                nextEnabled = true
                prevEnabled = true
            }
            6 -> {
                imageId++
                nextEnabled = false
            }
        }
    }
        Box (modifier = Modifier
            .fillMaxSize()
            .wrapContentWidth(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center){

            //spacer to add space between image and pic description
//            Spacer(modifier = Modifier.height(24.dp))
            //a column with the image description and year it was taken
            ImageDescription(imageResourceId = imageResourceId,contentDescription = contentDescription, photographer = photographer, yearTaken = yearTaken)
            //a row with the next and previous button
//            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
                Button(onClick = {onClickPrev(imageId)},
                    enabled = prevEnabled,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1.0f)) {
                    Text(text = "Previous" )
                }
                Spacer(modifier = Modifier.width(24.dp))
                Button(onClick = {onClickNext(imageId)},
                    enabled = nextEnabled,
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1.0f)) {
                    Text(text = "Next")
                }
            }
//            PrevAndNextButtons(imageId, onClickPrev = {imageId= it}, onClickNext = {imageId = it})
        }

}
@Composable
fun ImageDescription(imageResourceId: Int, contentDescription: String, photographer: String, yearTaken: String) {
    Column(modifier = Modifier
        .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter= painterResource(imageResourceId),
            contentDescription = contentDescription, modifier = Modifier.wrapContentHeight(Alignment.CenterVertically))
        Spacer(modifier = Modifier.height(16.dp))
        Column(modifier = Modifier
            .border(
                border = BorderStroke(1.dp, color = Color.White),
                shape = RectangleShape
            )) {

            Text(text = contentDescription,
                fontSize = 24.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(4.dp,4.dp,4.dp,0.dp))
            Text(text = buildAnnotatedString {
                withStyle(ParagraphStyle(lineHeight = 16.sp)){
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)){
                        append(photographer)
                    }
                    append(yearTaken)
                }
            },
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(4.dp,0.dp,4.dp,4.dp))
        }

    }
}
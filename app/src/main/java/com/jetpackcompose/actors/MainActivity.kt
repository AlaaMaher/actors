package com.jetpackcompose.actors

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.jetpackcompose.actors.data.RetrofitInstance
import com.jetpackcompose.actors.model.Character
import com.jetpackcompose.actors.repository.CharacterRepo
import com.jetpackcompose.actors.ui.theme.ActorsTheme
import com.jetpackcompose.actors.viewmodel.CharacterVM

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ActorsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val chAPI = RetrofitInstance.provideApi(RetrofitInstance.provideRetrofit())
                    val chRepo = CharacterRepo(chAPI)
                    val chVM = CharacterVM(chRepo)
                    MainScreen(chVM)
                }
            }
        }
    }
}

@Composable
fun MainScreen(viewModel:CharacterVM){
   val stateCharacters by viewModel.state.collectAsState()

    //Filter the actors list
    val nonEmptyList = mutableListOf<Character>()
    stateCharacters.forEach {
        if (it.image != ""){
            nonEmptyList.add(it)
        }
    }
    ActorsList(characterList = nonEmptyList)

}

@Composable
fun ActorsList(characterList: List<Character>){
    LazyVerticalGrid(columns = GridCells.Fixed(2),
        modifier = Modifier.padding(2.dp)){

        items(items=characterList){

            item -> CharItem(item)
        }

    }

}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CharItem(character: Character){
    Box (modifier = Modifier
        .fillMaxWidth(0.5f)
        .padding(horizontal = 16.dp, vertical = 16.dp)) {
        ImageCard(character.image, contentDescription = "Actors Photo", title = character.actor)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ImageCard(
    image: String,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {

        Box(
            modifier = Modifier.height(
                200.dp
            )
        ) {

            GlideImage(model = image, contentDescription = contentDescription, contentScale = ContentScale.Crop)


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            ) {
                Text(
                    modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 8.dp),
                    text = title,
                    fontSize = 16.sp,
                    style = TextStyle(Color.White)

                )
            }
        }
    }
}

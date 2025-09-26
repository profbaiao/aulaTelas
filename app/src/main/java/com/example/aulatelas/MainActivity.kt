package com.example.aulatelas

import android.R.attr.label
import android.R.attr.onClick
import android.R.attr.padding
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.carousel.HorizontalMultiBrowseCarousel
import androidx.compose.material3.carousel.rememberCarouselState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.aulatelas.ui.theme.AulaTelasTheme
import com.example.aulatelas.ui.theme.green

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AulaTelasTheme {
                App()
            }
        }
    }
}

class BottomAppBarItem(
    val icon: ImageVector,
    val label: String
)

class TopAppBarItem(
    var title: String,
    val icons: List<ImageVector> = emptyList()
)

sealed class ScreenItem(
    val topAppBarItem: TopAppBarItem,
    val bottomAppBarItem: BottomAppBarItem

) {
    data object Home : ScreenItem(
        topAppBarItem = TopAppBarItem(
            title = "Início",
            icons = listOf(
                Icons.Default.AccountCircle,
                Icons.Default.MoreVert,
            )
        ),
        bottomAppBarItem = BottomAppBarItem(
            icon = Icons.Default.Home,
            label = "Início"
        )
    )

    data object Events : ScreenItem(
        topAppBarItem = TopAppBarItem(
            title = "Eventos",
            icons = listOf(
                Icons.Default.Search,
                Icons.Default.MoreVert,
            )
        ),
        bottomAppBarItem = BottomAppBarItem(
            icon = Icons.Default.EventBusy,
            label = "Eventos"
        )
    )

    data object Contacts : ScreenItem(
        topAppBarItem = TopAppBarItem(
            title = "Contatos",
            icons = listOf(
                Icons.Default.Search,
                Icons.Default.MoreVert,
            )
        ),
        bottomAppBarItem = BottomAppBarItem(
            icon = Icons.Default.Contacts,
            label = "Contatos"
        )
    )

    data object Perfil : ScreenItem(
        topAppBarItem = TopAppBarItem(
            title = "Perfil",
            icons = listOf(
                Icons.Default.MoreVert,
            )
        ),
        bottomAppBarItem = BottomAppBarItem(
            icon = Icons.Default.Person,
            label = "Perfil"
        )
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun App() {
    val screens = remember {
        listOf(
            ScreenItem.Home,
            ScreenItem.Events,
            ScreenItem.Contacts,
            ScreenItem.Perfil

        )
    }

    var currentScreen by remember {
        mutableStateOf(screens.first())
    }

    val pagerState = rememberPagerState {
        screens.size
    }

    LaunchedEffect(currentScreen) {
        pagerState.animateScrollToPage(screens.indexOf(currentScreen))
    }

    LaunchedEffect(pagerState.targetPage) {
        currentScreen = screens[pagerState.targetPage]
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(title = {
                Text(currentScreen.topAppBarItem.title)
            }, actions = {
                Row(
                    Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    currentScreen.topAppBarItem.icons.forEach { icon ->
                        Icon(icon, contentDescription = null)

                    }
                }
            })
        },
        bottomBar = {
            BottomAppBar {
                screens.forEach { screen ->
                    with(screen.bottomAppBarItem) {
                        NavigationBarItem(
                            selected = screen == currentScreen,
                            onClick = {
                                currentScreen = screen
                            },
                            icon = { Icon(icon, contentDescription = null) },
                            label = { Text(label) }
                        )
                    }
                }
            }

        }

    ) { innerPadding ->

        HorizontalPager(
            pagerState,
            Modifier.padding(innerPadding)
        ) { page ->
            val item = screens[page]
            when (item) {
                ScreenItem.Contacts -> ContactsScreen()
                ScreenItem.Events -> EventsScreen()
                ScreenItem.Home -> HomeScreen()
                ScreenItem.Perfil -> PerfilScreen()
            }

        }


    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    Scaffold(
        floatingActionButton = {
            Example(
                onClick = { Log.d("FAB", "Clicked") }
            )
        }
    ) { padding ->
        Box(
            modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            Text(
                "Tela Principal",
                Modifier.align(Alignment.Center),
                style = TextStyle.Default.copy(
                    fontSize = 32.sp
                )
            )
        }


    }
}


@Composable
fun EventsScreen(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(4.dp)
        ) {
            Text(
                text = "Filled",
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }

        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(4.dp)
        ) {
            Text(
                text = "Outlined",
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }

        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(4.dp)
        ) {
            Text(
                text = "Elevated",
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(modifier: Modifier = Modifier) {
    data class CarouselItem(
        val id: Int,
        @DrawableRes val imageResId: Int,
        val contentDescription: String
    )

    val items = remember {
        listOf(
            CarouselItem(0, R.drawable.cupcake, "cupcake"),
            CarouselItem(1, R.drawable.donut, "donut"),
            CarouselItem(2, R.drawable.eclair, "eclair"),
            CarouselItem(3, R.drawable.froyo, "froyo"),
            CarouselItem(4, R.drawable.gingerbread, "gingerbread"),
        )
    }

    Column {

        HorizontalMultiBrowseCarousel(
            state = rememberCarouselState { items.count() },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 16.dp, bottom = 16.dp),
            preferredItemWidth = 186.dp,
            itemSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) { i ->
            val item = items[i]
            Image(
                modifier = Modifier
                    .height(205.dp)
                    .maskClip(MaterialTheme.shapes.extraLarge),
                painter = painterResource(id = item.imageResId),
                contentDescription = item.contentDescription,
                contentScale = ContentScale.Crop
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            HorizontalDivider(thickness = 2.dp)
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            repeat(30) {
                Text("Item $it", modifier = Modifier.padding(2.dp))
            }
        }
    }
}

@Composable
fun PerfilScreen(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) {
        var text by remember { mutableStateOf("") }
        var isToggled by rememberSaveable { mutableStateOf(false) }

        TextField(
            value = text,
            label = { Text("Label") },
            onValueChange = { newText ->
                text = newText
            }
        )
        OutlinedTextField(
            value = text,
            label = { Text("Label") },
            onValueChange = { newText ->
                text = newText
            }
        )

        IconButton(
            onClick = { isToggled = !isToggled }
        ) {
            Icon(
                painter = if (isToggled) painterResource(R.drawable.favorite_filled) else painterResource(R.drawable.favorite),
                contentDescription = if (isToggled) "Selected icon button" else "Unselected icon button."
            )
        }
        Button(onClick = { }) {
            Text("Filled")
        }
        FilledTonalButton(onClick = { }) {
            Text("Tonal")
        }
        ElevatedButton(onClick = {  }) {
            Text("Elevated")
        }
        TextButton(
            onClick = { }
        ) {
            Text("Text Button")
        }
    }
}

@Preview
@Composable
private fun AppPreview() {
    AulaTelasTheme {
        App()
    }
}

@Composable
fun Example(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = green,
        contentColor = Color.White,


        ) {
        Icon(Icons.Filled.Add, "Floating action button.")
    }
}
package com.john.nycschools.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import com.john.nycschools.R
import com.john.nycschools.models.School
import com.john.nycschools.ui.ErrorScreen
import com.john.nycschools.ui.LoadingScreen
import com.john.nycschools.ui.components.AppDivider
import com.john.nycschools.ui.navigation.NavigationDestination
import com.john.nycschools.ui.theme.Blue20
import com.john.nycschools.ui.theme.md_theme_light_inversePrimary
import com.john.nycschools.ui.theme.md_theme_light_outline
import com.john.nycschools.ui.theme.md_theme_light_secondaryContainer
import com.john.nycschools.ui.theme.md_theme_light_surfaceTint
import com.john.nycschools.utils.mirroringBackIcon
import kotlin.math.max
import kotlin.math.min

private val TitleHeight = 128.dp
private val GradientScroll = 180.dp
private val ImageOverlap = 115.dp
private val MinTitleOffset = 56.dp
private val MinImageOffset = 12.dp
private val MaxTitleOffset = ImageOverlap + MinTitleOffset + GradientScroll
private val ExpandedImageSize = 300.dp
private val CollapsedImageSize = 150.dp
private val HzPadding = Modifier.padding(horizontal = 24.dp)


object DetailsScreenDestination : NavigationDestination {
    override val route: String = "details"
    override val titleRes = R.string.school_details
    const val schoolObj = "schoolObj"
    val routeWithArgs = "$route?schoolObj={$schoolObj}"
}

@Composable
fun DetailsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    when(val detailsUiState = viewModel.detailsUiState) {
        is DetailsUiState.Loading -> LoadingScreen(
            modifier
                .fillMaxSize()
                .size(200.dp)
        )
        is DetailsUiState.Success ->
            DetailsScreenMain(
                navigateBack = navigateBack,
                school = detailsUiState.school,
            )
        is DetailsUiState.Error ->
            ErrorScreen(
                modifier = modifier,
                detailsUiState.message
            )
    }
}

@Composable
private fun DetailsScreenMain(
    navigateBack: () -> Unit,
    school: School
) {
    Box(Modifier.fillMaxSize()) {
        val scroll = rememberScrollState(0)
        Header()
        Body(school, scroll)
        Title(school) { scroll.value }
        CollapsingImage { scroll.value }
        BackNavigationIcon(navigateBack)
    }
}


@Composable
private fun Header() {
    Spacer(
        modifier = Modifier
            .height(280.dp)
            .fillMaxWidth()
            .background(
                Brush.horizontalGradient(
                    listOf(
                        md_theme_light_inversePrimary,
                        md_theme_light_surfaceTint
                    )
                )
            )
    )
}

@Composable
private fun BackNavigationIcon(navigateBack: () -> Unit) {
    IconButton(
        onClick = navigateBack,
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 10.dp)
            .size(36.dp)
            .background(
                color = md_theme_light_secondaryContainer.copy(alpha = 0.32f),
                shape = CircleShape
            )
    ) {
        Icon(
            imageVector = mirroringBackIcon(),
            tint = md_theme_light_outline,
            contentDescription = stringResource(R.string.back)
        )
    }
}

@Composable
private fun Body(
    school: School,
    scroll: ScrollState
) {
    Column {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(70.dp)
        )
        Column(
            modifier = Modifier.verticalScroll(scroll)
        ) {
            Spacer(Modifier.height(GradientScroll))
            Surface(Modifier.fillMaxWidth()) {
                Column {
                    Spacer(Modifier.height(ImageOverlap))
                    Spacer(Modifier.height(TitleHeight))
                    Spacer(Modifier.height(16.dp))
                    var seeMore by remember { mutableStateOf(true) }
                    Text(
                        text = school.overview_paragraph,
                        style = MaterialTheme.typography.body1,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.outline,
                        maxLines = if (seeMore) 5 else Int.MAX_VALUE,
                        overflow = TextOverflow.Ellipsis,
                        modifier = HzPadding
                    )
                    val textButton = if (seeMore) {
                        stringResource(id = R.string.see_more)
                    } else {
                        stringResource(id = R.string.see_less)
                    }
                    Text(
                        text = textButton,
                        style = MaterialTheme.typography.button,
                        textAlign = TextAlign.Center,
                        color = Blue20,
                        modifier = Modifier
                            .heightIn(20.dp)
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                            .clickable {
                                seeMore = !seeMore
                            }
                    )
                    Spacer(Modifier.height(40.dp))

                    Text(
                        text = stringResource(id = R.string.extra_curricular_activities),
                        style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = HzPadding
                    )

                    Text(
                        text = school.extracurricular_activities,
                        style = MaterialTheme.typography.body1,
                        modifier = HzPadding
                    )

                    Spacer(Modifier.height(16.dp))
                    AppDivider()

                    SchoolSATDetails(school)
                }
            }
        }
    }
}

@Composable
private fun SchoolSATDetails(school: School) {
    school.details?.let {
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(
                R.string.sat_score_details
            ),
            style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = HzPadding
        )
        Text(
            text = stringResource(
                R.string.number_of_test_taker,
                it.num_of_sat_test_takers
            ),
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = androidx.compose.material3.MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(start = 32.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(
                R.string.sat_average_score,
            ),
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = HzPadding
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(
                R.string.reading,
                it.sat_critical_reading_avg_score
            ),
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = androidx.compose.material3.MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(start = 32.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(
                R.string.math,
                it.sat_math_avg_score
            ),
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = androidx.compose.material3.MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(start = 32.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(
                R.string.writing,
                it.sat_writing_avg_score
            ),
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = androidx.compose.material3.MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(start = 32.dp)
        )
        Spacer(Modifier.height(16.dp))
        AppDivider()
        Spacer(Modifier.height(16.dp))
    }
}



@Composable
private fun Title(school: School, scrollProvider: () -> Int) {
    val maxOffset = with(LocalDensity.current) { MaxTitleOffset.toPx() }
    val minOffset = with(LocalDensity.current) { MinTitleOffset.toPx() }

    Column(
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier
            .heightIn(min = TitleHeight)
            .statusBarsPadding()
            .offset {
                val scroll = scrollProvider()
                val offset = (maxOffset - scroll).coerceAtLeast(minOffset)
                IntOffset(x = 0, y = offset.toInt())
            }
    ) {
        Spacer(Modifier.height(16.dp))
        Text(
            text = school.school_name,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            modifier = HzPadding
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = school.city.plus(" , ")
                .plus(school.state_code)
                .plus(" ")
                .plus(school.zip),
            style = MaterialTheme.typography.subtitle1,
            modifier = HzPadding
        )
        Text(
            text = school.phone_number,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.padding(start = 24.dp)
        )

        Spacer(Modifier.height(8.dp))
        AppDivider()
    }
}

@Composable
private fun CollapsingImage(
    scrollProvider: () -> Int
) {
    val collapseRange = with(LocalDensity.current) { (MaxTitleOffset - MinTitleOffset).toPx() }
    val collapseFractionProvider: () -> Float = {
        (scrollProvider() / collapseRange).coerceIn(0f, 1f)
    }

    CollapsingImageLayout(
        collapseFractionProvider = collapseFractionProvider,
        modifier = HzPadding.statusBarsPadding()
    ) {
        CircleImage(
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun CircleImage(
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 0.dp
) {
    Surface(
        color = Color.LightGray,
        elevation = elevation,
        shape = CircleShape,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.demo_image),
            contentDescription = contentDescription,
            modifier = Modifier
                .width(100.dp)
                .height(100.dp),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun CollapsingImageLayout(
    collapseFractionProvider: () -> Float,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        check(measurables.size == 1)

        val collapseFraction = collapseFractionProvider()

        val imageMaxSize = min(ExpandedImageSize.roundToPx(), constraints.maxWidth)
        val imageMinSize = max(CollapsedImageSize.roundToPx(), constraints.minWidth)
        val imageWidth = lerp(imageMaxSize, imageMinSize, collapseFraction)
        val imagePlaceable = measurables[0].measure(Constraints.fixed(imageWidth, imageWidth))

        val imageY = lerp(MinTitleOffset, MinImageOffset, collapseFraction).roundToPx()
        val imageX = lerp(
            (constraints.maxWidth - imageWidth) / 2, // centered when expanded
            constraints.maxWidth - imageWidth, // right aligned when collapsed
            collapseFraction
        )
        layout(
            width = constraints.maxWidth,
            height = imageY + imageWidth
        ) {
            imagePlaceable.placeRelative(imageX, imageY)
        }
    }
}

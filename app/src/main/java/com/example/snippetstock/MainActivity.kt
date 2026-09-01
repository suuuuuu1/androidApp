package com.example.snippetstock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.snippetstock.ui.theme.SnippetStockTheme

// スニペット1件ぶんのデータの形
data class Snippet(
    val title: String,
    val tags: List<String>,
    val code: String,
)

// いまは仮データ。あとでDBや保存機能に置き換える
val sampleSnippets = listOf(
    Snippet(
        title = "二分探索 (めぐる式)",
        tags = listOf("探索", "O(logN)"),
        code = "var ng = -1; var ok = n\nwhile (ok - ng > 1) {\n    val mid = (ng + ok) / 2\n    if (isOk(mid)) ok = mid else ng = mid\n}",
    ),
    Snippet(
        title = "Union-Find",
        tags = listOf("データ構造", "グラフ"),
        code = "class UnionFind(n: Int) {\n    val parent = IntArray(n) { it }\n    fun find(x: Int): Int = if (parent[x] == x) x else find(parent[x]).also { parent[x] = it }\n    fun union(a: Int, b: Int) { parent[find(a)] = find(b) }\n}",
    ),
    Snippet(
        title = "累積和",
        tags = listOf("前処理"),
        code = "val s = LongArray(n + 1)\nfor (i in 0 until n) s[i + 1] = s[i] + a[i]\n// [l, r) の和 = s[r] - s[l]",
    ),
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SnippetStockTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SnippetList(
                        snippets = sampleSnippets,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

// スニペットのリスト表示。LazyColumn = 画面に見えてる分だけ描画するスクロールリスト
@Composable
fun SnippetList(snippets: List<Snippet>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(snippets) { snippet ->
            SnippetCard(snippet)
        }
    }
}

// スニペット1件ぶんのカード
@Composable
fun SnippetCard(snippet: Snippet, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = snippet.title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = snippet.tags.joinToString(" / "),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = snippet.code,
                style = MaterialTheme.typography.bodySmall,
                fontFamily = FontFamily.Monospace,
            )
        }
    }
}

// Android Studio 内でエミュレータ無しで見た目を確認できるプレビュー
@Preview(showBackground = true)
@Composable
fun SnippetListPreview() {
    SnippetStockTheme {
        SnippetList(snippets = sampleSnippets)
    }
}
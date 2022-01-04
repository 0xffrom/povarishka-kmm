import SwiftUI
import shared

struct ContentView: View {
    @State var recipeResource: Resource<RecipeDetails> = ResourceLoading<RecipeDetails>()

    let viewModel: RecipesViewModel

    init() {
        viewModel = RecipesViewModel()

        viewModel.observe { [self] (v: RecipesState) in
            recipeResource = v.recipeResource
        }

        viewModel.loadMore()
    }

    var body: some View {
        if (recipeResource is ResourceLoading) {
            getLoadingScreen()
        } else if (recipeResource is ResourceError) {
            getErrorScreen(throwable: (recipeResource as! ResourceError<RecipeDetails>).throwable)
        } else if (recipeResource is ResourceData) {
            getRecipeDetailsScreen(recipeDetails: (recipeResource as! ResourceData).data!)
        }
    }

    func getLoadingScreen() -> some View {
        VStack {
            ProgressView()
                    .progressViewStyle(CircularProgressViewStyle())
        }
    }

    func getErrorScreen(throwable: KotlinThrowable) -> some View {
        VStack {
            Text(throwable.message ?? "Error")
            Spacer(minLength: 16)
            Button("Reload") {
                viewModel.loadMore()
            }
        }
    }

    func getRecipeDetailsScreen(recipeDetails: RecipeDetails) -> some View {
        VStack {
            AsyncImage(url: URL(string: recipeDetails.image)!,
                    placeholder: { Text("Loading ...") },
                    image: { Image(uiImage: $0).resizable()})
                    .clipShape(Circle())
                    .shadow(radius: 10)
                    .scaledToFit()
                    .frame(width: 320.0, height: 320.0)

            Spacer(minLength: 16)
            Text(recipeDetails.title)
            Spacer(minLength: 8)
            HTMLStringView(htmlContent: recipeDetails.summary).padding(12)
            Spacer(minLength: 16)
            Button("Load more") {
                viewModel.loadMore()
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
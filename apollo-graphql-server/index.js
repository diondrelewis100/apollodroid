const fetch = require('node-fetch');
const { ApolloServer, gql } = require('apollo-server');

// A schema is a collection of type definitions (hence "typeDefs")
// that together define the "shape" of queries that are executed against
// your data.
const typeDefs = gql`
type Movie {
id: String
original_title: String
poster_path: String
vote_average: String
}

type Query {
movies: [Movie]
}
`;

// fetch api of TMDB to fetch data of popular movies
const fetchPopularMovies = async () => {
    return await fetch('https://api.themoviedb.org/3/movie/popular?api_key=73706a343826bd5f584c9e01467362ee')
        .then(response => response.json())
        .then(data => {
            // console.log(data)
            return data.results
        })
        .catch(err => console.error)
}

// Resolvers define the technique for fetching the types defined in the
// schema. This resolver retrieves books from the "books" array above.
const resolvers = {
    Query: {
        movies: fetchPopularMovies,
    },
};

// The ApolloServer constructor requires two parameters: your schema
// definition and your set of resolvers.
const server = new ApolloServer({ typeDefs, resolvers });

// The `listen` method launches a web server.
server.listen().then(({ url }) => {
    console.log(`Server ready at ${url}`);
});


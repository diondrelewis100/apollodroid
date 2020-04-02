const jwt = require('jsonwebtoken');
const fetch = require('node-fetch');
const { ApolloServer, gql } = require('apollo-server');

// Load environment variables.
require('dotenv').config();

const TMDB_API_KEY = '73706a343826bd5f584c9e01467362ee';

// A schema is a collection of type definitions (hence "typeDefs")
// that together define the "shape" of queries that are executed against
// your data.
const typeDefs = gql`
type Review {
    id: String
    author: String
    content: String
    url: String
}

type Movie {
id: String
original_title: String
poster_path: String
vote_average: String
reviews: [Review]
}

type Query {
movies: [Movie]
}
`;

// fetch api of TMDB to fetch data of popular movies
const fetchPopularMovies = async () => {
    return await fetch(`https://api.themoviedb.org/3/movie/popular?api_key=${TMDB_API_KEY}`)
        .then(response => response.json())
        .then(data => {
            const reviewPromises = data.results.map((_movie) => fetchMovieReviews(_movie))
            return Promise.all(reviewPromises)
        })
        .catch(err => console.error)
}

const fetchMovieReviews = (movie) => 
    fetch(`https://api.themoviedb.org/3/movie/${movie.id}/reviews?api_key=${TMDB_API_KEY}`)
    .then(response => response.json())
    .then(movieReviews => {
        movie.reviews = movieReviews.results;
        return movie;
    })

// verify jwt token and decode user object
const getUser = (token) => 
     new Promise((resolve, reject) => {
         if (token === process.env.ADMIN_TOKEN) {
             resolve({loggedIn: true});
         } else {
             jwt.verify(token, process.env.JWT_SECRET, (err, decoded) => {
                if (err || !decoded.loggedIn) {
                resolve(null);
                return;
                }
                
                resolve(decoded);
            });
         }
    });
// }

// Resolvers define the technique for fetching the types defined in the
// schema. This resolver retrieves books from the "books" array above.
const resolvers = {
    Query: {
        movies: fetchPopularMovies,
    },
};

// The ApolloServer constructor requires two parameters: your schema
// definition and your set of resolvers.
const server = new ApolloServer({ 
    typeDefs, 
    resolvers,
    context: async ({ req }) => {
        // Note! This example uses the `req` object to access headers,
        // but the arguments received by `context` vary by integration.
        // This means they will vary for Express, Koa, Lambda, etc.!
        //
        // To find out the correct arguments for a specific integration,
        // see the `context` option in the API reference for `apollo-server`:
        // https://www.apollographql.com/docs/apollo-server/api/apollo-server/
     
        // Get the user token from the headers.
        const token = req.headers.authorization || '';
     
        // try to retrieve a user with the token
        const user = await getUser(token);

        // optionally block the user
        // we could also check user roles/permissions here
        if (!user) throw new AuthenticationError('you must be logged in');
     
        // add the user to the context
        return { user };
      }
     });

// The `listen` method launches a web server.
server.listen().then(({ url }) => {
    console.log(`Server ready at ${url}`);
});


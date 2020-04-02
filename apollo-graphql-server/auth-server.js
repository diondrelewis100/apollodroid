
const bodyParser = require('body-parser');
const jwt = require('jsonwebtoken');
var express = require("express");
var app = express();

// Load environment variables.
require('dotenv').config();

// configure express app to use body parsers
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

// Login user post api call
app.post(`/login`, (req, res, next) => {
    let request = req.body;

    // validate username and password. This can be validated with db or any other third party auth service
    if (request.username === "diondre@ibgtraining.com" && request.password === "123456") {
        const resBody = {
            loggedIn: true,
            user: request.username
        };

        // create jwt token and embed encrypted user content into it
        resBody.token = jwt.sign(resBody, process.env.JWT_SECRET, { expiresIn: process.env.JWT_EXPIRY });
        res.status(200)
        .json(resBody);
    } else {

        // invalid credentials, failure response
        res.status(401)
        .json({
            error: "Invalid username or password"
        });
    }

});

app.listen(3000, () => {
 console.log("Server running on port 3000");
});
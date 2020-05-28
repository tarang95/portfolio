//using the node libreries
const express = require('express');
const bodyParser = require('body-parser');
const dbConfig = require('./config');
const mongoose = require('mongoose');
// create express app
const app = express();

// parse requests of content-type - application/x-www-form-urlencoded
app.use(bodyParser.urlencoded({ extended: true }));

// parse requests of content-type - application/json
app.use(bodyParser.json());
mongoose.Promise = global.Promise;

// Connecting to the database
mongoose.connect(dbConfig.url)
.then(() => {
    console.log("database connected successfully");    
}).catch(err => {
    console.log('Could not connect to the database. Exiting now...');
    process.exit();
});

// define a simple route
app.get('/', (req, res) => {
    res.json({"message": "Welcome to city logs service."});
});
require('./common')(app);
// listen for requests
app.listen(3000, () => {
    console.log("Server is listening on port 3000");
});
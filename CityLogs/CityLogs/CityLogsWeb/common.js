const mongoose = require('mongoose');
const fs = require('fs');
const city_logs = mongoose.Schema({
    city: String,
    time: String,
    latitude: String,
    longitude: String,
    contact: String,
    invoice: String,
    destination: String
});
const city_log_model = mongoose.model('city_logs', city_logs);

module.exports = (app) => {
    // Retrieve all Notes
    app.get('/city', (req, res) => {
        res.header("Access-Control-Allow-Origin", "*");
        res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        res.send("into city action");
    });
    app.post('/city/:city/log', (req, res) => {
        res.header("Access-Control-Allow-Origin", "*");
        res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        var result = {};
        if (req.body.city_logs != null) {
            console.log(req.body.city_logs);
            for (const key in req.body.city_logs) {
                req.body.city_logs[key].city = req.params.city;
            }
            console.log(req.body.city_logs);
           
            fs.appendFile('logs/logs.dat',JSON.stringify(req.body.city_logs), function (err) {
                if (err)
                    return console.log(err);
                console.log('log written to ');
            });
            city_log_model.insertMany(req.body.city_logs)
                .then(function (docs) {
                    // send success message
                    result.ok = true;
                    result.data = docs;
                    res.send(result);
                })
                .catch(function (err) {
                    // send error message
                    result.ok = false;
                    result.data = [];
                    res.send(result);
                });
        } else {
            result.ok = false;
            result.data = [];
            res.send(result);
        }
    });
    app.get('/city/search/:query', (req, res) => {
        var result = {};
        res.header("Access-Control-Allow-Origin", "*");
        res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        // Validate request
        city_log_model.find({
            city: req.params.query
        }).then(data => {
            result.ok = true;
            result.data = data;
            res.send(result);
        }).catch(err => {
            result.ok = false;
            result.data = [];
            res.send(result);
        });
    });
}

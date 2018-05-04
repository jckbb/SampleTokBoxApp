import React, { Component } from 'react';
import { View, NativeModules } from 'react-native';

class App extends Component {
    componentWillMount() {
        NativeModules.StartActivity.navigateToActivity();
    }

    render() {
        return (
            <View style={{flex: 1}} />
        );
    }
}

export default App;
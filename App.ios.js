import React, { Component } from 'react';
import { View, requireNativeComponent } from 'react-native';

const TokBoxComponent = requireNativeComponent('RNTTokBox');

class App extends Component {
    render() {
        return (
            <View style={{flex: 1}}>
                <TokBoxComponent style={{flex: 1}} />
            </View>
        );
    }
}

export default App;
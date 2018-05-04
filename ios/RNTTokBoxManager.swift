//
//  RNTTokBoxManager.swift
//  TaskTokBoxApp
//
//  Created by joseph burghard on 3/31/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

@objc (RNTTokBoxManager)
class RNTTokBoxManager: RCTViewManager {
  override func view() -> UIView! {
    return MyTokBoxView();
  }
}

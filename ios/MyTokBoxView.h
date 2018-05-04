//
//  MyTokBoxView.h
//  TaskTokBoxApp
//
//  Created by joseph burghard on 3/31/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <OpenTok/OpenTok.h>
#import <React/RCTLog.h>

@interface MyTokBoxView : UIView <OTSessionDelegate, OTPublisherDelegate, OTSubscriberDelegate>
    @property (nonatomic) OTSession *session;
    @property (nonatomic) OTPublisher *publisher;
    @property (nonatomic) OTSubscriber *subscriber;
@end

//
//  MyTokBoxView.m
//  TaskTokBoxApp
//
//  Created by joseph burghard on 3/31/18.
//  Copyright © 2018 Facebook. All rights reserved.
//

#import "MyTokBoxView.h"

// Replace with your OpenTok API key
static NSString* kApiKey = @"46091842";
// Replace with your generated session ID
static NSString* kSessionId = @"2_MX40NjA5MTg0Mn5-MTUyMjQ1MTk4MjQwMn40K05TYTlURlc3STZXRnBnRXd1VFFYN0h-UH4";
// Replace with your generated token
static NSString* kToken = @"T1==cGFydG5lcl9pZD00NjA5MTg0MiZzaWc9ODBkZjQyZTM4Y2JlY2RiY2JhYzcwZTg1OWZmNWVkNmIzNjllYjU0YzpzZXNzaW9uX2lkPTJfTVg0ME5qQTVNVGcwTW41LU1UVXlNalExTVRrNE1qUXdNbjQwSzA1VFlUbFVSbGMzU1RaWFJuQm5SWGQxVkZGWU4waC1VSDQmY3JlYXRlX3RpbWU9MTUyMjQ3MTgxMyZub25jZT0wLjQzMDgyNDQ5OTcwODk3Mjc0JnJvbGU9cHVibGlzaGVyJmV4cGlyZV90aW1lPTE1MjMwNzY2MTImaW5pdGlhbF9sYXlvdXRfY2xhc3NfbGlzdD0=";

@implementation MyTokBoxView
    - (instancetype)init {
        self = [super init];
      
        if (self) {
            [self connectToAnOpenTokSession];
        }
      
        return self;
    }

    - (void)connectToAnOpenTokSession {
        _session = [[OTSession alloc] initWithApiKey:kApiKey sessionId:kSessionId delegate:self];
      
        NSError *error;
      
      [_session connectWithToken:kToken error:&error];
    }

    # pragma mark - OTSession delegate callbacks
    - (void)sessionDidConnect:(OTSession*)session {
        _publisher = [[OTPublisher alloc]
                      initWithDelegate:self];
      
        OTError *error = nil;
        [_session publish:_publisher error:&error];
      
        if (error) {
            return;
        }
      
        CGSize screenSize = [UIScreen mainScreen].bounds.size;
        CGRect rect = CGRectMake(screenSize.width - 150 - 20, screenSize.height - 150 - 20, 150, 150);
        [_publisher.view setFrame:rect];
        [self addSubview:_publisher.view];
    }

    - (void)sessionDidDisconnect:(OTSession*)session {}

    - (void) session:(OTSession*)session
      didFailWithError:(OTError*)error {}

    - (void)session:(OTSession*)session
      streamCreated:(OTStream *)stream {
        _subscriber = [[OTSubscriber alloc] initWithStream:stream delegate:self];
        OTError *error = nil;
        [_session subscribe:_subscriber error:&error];
      
        if (error) {
            return;
        }
      
        [_subscriber.view setFrame:[UIScreen mainScreen].bounds];
        [self insertSubview:_subscriber.view atIndex:0];
    }

    - (void)session:(OTSession*)session
      streamDestroyed:(OTStream *)stream {}

    # pragma mark - OTPublisher delegate callbacks
    - (void)publisher:(OTPublisherKit*)publisher
     didFailWithError:(OTError*) error {}

    # pragma mark - OTSubscriber delegate callbacks
    - (void)subscriberDidConnectToStream:(OTSubscriberKit *)subscriber {}

    - (void)subscriber:(OTSubscriberKit*)subscriber
      didFailWithError:(OTError*)error {}
@end

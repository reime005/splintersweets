//
// Google Play Games Platform Services
// Copyright 2013 Google Inc. All rights reserved.
//
#import <UIKit/UIKit.h>

#import "GPGEnums.h"

@class GPGPlayerStats;

typedef void (^GPGPlayerStatsGetBlock)(GPGPlayerStats *playerStats, NSError *error);

@interface GPGPlayerStats : NSObject

@property(nonatomic, readonly, assign) float averageSessionLength;

@property(nonatomic, readonly, assign) float churnProbability;

@property(nonatomic, readonly, assign) int64_t daysSinceLastPlayed;

@property(nonatomic, readonly, assign) int64_t numberOfPurchases;

@property(nonatomic, readonly, assign) int64_t numberOfSessions;

@property(nonatomic, readonly, assign) float sessionPercentile;

@property(nonatomic, readonly, assign) float spendPercentile;

+ (void)playerStatsWithCompletionHandler:(GPGPlayerStatsGetBlock)completionHandler;

+ (void)playerStatsFromDataSource:(GPGDataSource)dataSource
                completionHandler:(GPGPlayerStatsGetBlock)completionHandler;

@end

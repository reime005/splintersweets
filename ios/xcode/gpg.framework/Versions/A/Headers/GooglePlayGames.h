//
// Google Play Games Platform Services
// Copyright 2013 Google Inc. All rights reserved.
//
#import <Foundation/Foundation.h>

#if __IPHONE_OS_VERSION_MIN_REQUIRED < __IPHONE_7_0
#pragma message "Google Play Game Services SDK requires iOS 7.0 or later."
#endif

#import <gpg/GPGEnums.h>

#import <gpg/GPGAchievement.h>
#import <gpg/GPGAchievementMetadata.h>
#import <gpg/GPGEvent.h>
#import <gpg/GPGLauncherController.h>
#import <gpg/GPGLeaderboard.h>
#import <gpg/GPGLeaderboardMetadata.h>
#import <gpg/GPGLocalPlayerRank.h>
#import <gpg/GPGLocalPlayerScore.h>
#import <gpg/GPGManager.h>
#import <gpg/GPGMultiplayerConfig.h>
#import <gpg/GPGPlayer.h>
#import <gpg/GPGPlayerLevel.h>
#import <gpg/GPGPlayerStats.h>
#import <gpg/GPGQuest.h>
#import <gpg/GPGRealTimeRoom.h>
#import <gpg/GPGRealTimeRoomData.h>
#import <gpg/GPGRealTimeRoomMaker.h>
#import <gpg/GPGScore.h>
#import <gpg/GPGScoreReport.h>
#import <gpg/GPGSnapshotMetadata.h>
#import <gpg/GPGSnapshotMetadataChange.h>
#import <gpg/GPGTurnBasedMatch.h>
#import <gpg/GPGTurnBasedParticipant.h>
#import <gpg/GPGTurnBasedParticipantResult.h>

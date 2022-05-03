% ------------ DOMAINS ------------------------------------------ %
time(0..20).
track(side(0..8);main(0..8)).
object(g1;g2;g3).
group(g1;g2;g3).
numberInGroup(g1,5;g2,2;g3,1).

buttonOn(main(0)).
buttonOn(side(3)).
bridgeOn(b,main(4)).


% ------------ INITIAL SITUATION -------------------------------- %
initially(on(g1,main(6))).
initially(on(g2,side(7))).
initially(on(g3,b)).
initially(alive(g1;g2;g3)).
initially(on(train,main(0))).

% ------------ SCENARIOS ---------------------------------------- %
sim(s0).
performs(s0,push(g3,b),0).

% ------------ EVENT SPECIFICATION ------------------------------ %
act(switch(main(0))).
prec(on(train,main(0)),switch(main(0))).
effect(switch(main(0)),neg(on(train,main(0)))).
effect(switch(main(0)),on(train,side(0))).

act(switch(side(3))).
prec(on(train,side(3)),switch(side(3))).
effect(switch(side(3)),neg(on(train,side(3)))).
effect(switch(side(3)),on(train,main(1))).


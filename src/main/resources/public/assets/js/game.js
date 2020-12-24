// TODO: Browser compatability.

const game = {

    ready: false,

    mobileControls: {
        up: false,
        direction: 0
    },

    players: new Map(),

    onload: function () {
        if (!me.video.init(640, 480, {parent : "screen", scale : "auto", scaleMethod : "flex-width"})) {
            alert("Your browser does not support HTML5 canvas.");
            return;
        }
        me.loader.preload(game.resources, game.loaded);      
    },

    loaded: function () {

        game.animations = {
            santa: new me.video.renderer.Texture(
                me.loader.getJSON("santa_animation_data"),
                me.loader.getImage("santa_animation")
            )
        };

        me.state.set(me.state.PLAY, new game.PlayScreen());

        me.pool.register("playerEntity", game.PlayerEntity, true);

        me.input.bindKey(me.input.KEY.LEFT,  "left");
        me.input.bindKey(me.input.KEY.RIGHT, "right");
        me.input.bindKey(me.input.KEY.UP, "up");
        me.input.bindKey(me.input.KEY.DOWN, "down");


        if (mobileCheck()) {
            GameController.init({
                left: {
                    type: "joystick",
                    joystick: {
                        max: 500,
                        radius: 100,
                        touchEnd: () => game.mobileControls.direction = 0,
                        touchMove: details => {
                            if (details.normalizedX > 0) {
                                game.mobileControls.direction = 1;
                            } else if (details.normalizedX < 0) {
                                game.mobileControls.direction = -1;
                            } else {
                                game.mobileControls.direction = 0;
                            }
                        }
                    }
                },
                right: {
                    type: "buttons",
                    buttons: [
                        {
                            radius: 100,
                            label: "Jump",
                            touchStart: () => game.mobileControls.up = true,
                            touchEnd: () => game.mobileControls.up = false,
                            offset: {
                                y: -70
                            }
                        }, false, false, false
                    ]
                }
            });
        }

        me.state.change(me.state.PLAY);
        
    }
    
};

game.socket = new WebSocket(`ws://${window.location.hostname}:${window.location.port || '80'}/game`);

game.socket.onopen = () => {

    if (game.ready) {
        game.socket.send(JSON.stringify({
            id: 0,
            packet: {
                name: "TODO",
                colorType: -1
            }
        }));
    }
};

game.socket.onmessage = message => {
    const wrapper = JSON.parse(message.data);
    switch (wrapper.id) {
        case 1:
            game.players.get(wrapper.packet.playerId).x = wrapper.packet.x;
            game.players.get(wrapper.packet.playerId).y = wrapper.packet.y;
        break;
        case 4:
            const newPlayer = me.pool.pull('playerEntity', wrapper.packet.x, wrapper.packet.y, {
                width: 128,
                height: 128,
                playerId: wrapper.packet.playerId
            });
            game.players.set(wrapper.packet.playerId, { x: wrapper.packet.x, y: wrapper.packet.y, entity: newPlayer });
            me.game.world.addChild(newPlayer);
        break;
        case 5:
            me.game.world.removeChild(game.players.get(wrapper.packet.playerId).entity);
            game.players.delete(wrapper.packet.playerId);
        break;
    }
};

game.PlayScreen = me.Stage.extend({

    onResetEvent: function () {

        if (game.socket.readyState === WebSocket.OPEN) {
            game.socket.send(JSON.stringify({
                    id: 0,
                    packet: {
                        name: "TODO",
                        colorType: -1
                    }
            }));
        }
        game.ready = true;

        me.levelDirector.loadLevel("main_map");

        const player = me.pool.pull('playerEntity', 700, 1300, {
            width: 128,
            height: 128,
            playerId: null
        });
        me.game.world.addChild(player);


    },

    onDestroyEvent: function () {

    }

});